package com.bah.engine.service;

import com.bah.engine.configuration.exception.AccountNotFoundException;
import com.bah.engine.configuration.exception.CurrencyMismatchException;
import com.bah.engine.configuration.exception.InsufficientBalanceException;
import com.bah.engine.entity.Account;
import com.bah.engine.entity.AccountTransaction;
import com.bah.engine.entity.mapper.AccountMapper;
import com.bah.engine.entity.mapper.AccountTransactionMapper;
import com.bah.engine.enums.TransactionType;
import com.bah.engine.helper.ExchangeHelper;
import com.bah.engine.helper.UserHelper;
import com.bah.engine.model.*;
import com.bah.engine.repository.AccountRepository;
import com.bah.engine.repository.AccountTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountTransactionRepository accountTransactionRepository;
    private final UserHelper userHelper;
    private final ExternalCallService externalCallService;

    @Override
    @Transactional
    public AccountDto createAccount(CreateAccountRequest request) {

        Account account = new Account();
        account.setUserId(userHelper.getCurrentUser().getId());
        account.setName(request.getName());
        account.setCurrency(request.getCurrency());

        accountRepository.save(account);
        return AccountMapper.INSTANCE.toDto(account);
    }

    @Override
    @Transactional
    public AccountDto deposit(Integer accountId, DepositRequestDto request) {
        Account account = getAccount(accountId);
        if (account.getCurrency() != request.getCurrency()) {
            throw new CurrencyMismatchException();
        }
        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepository.save(account);
        saveTransaction(account, TransactionType.DEPOSIT, request.getAmount());
        return AccountMapper.INSTANCE.toDto(account);
    }

    @Override
    @Transactional
    public AccountDto debit(Integer accountId, DebitRequestDto request) {
        Account account = getAccount(accountId);
        if (account.getCurrency() != request.getCurrency()) {
            throw new CurrencyMismatchException();
        }
        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException();
        }

        externalCallService.logDebit();

        account.setBalance(account.getBalance().subtract(request.getAmount()));
        accountRepository.save(account);
        saveTransaction(account, TransactionType.DEBIT, request.getAmount());
        return AccountMapper.INSTANCE.toDto(account);
    }

    @Override
    public BalanceResponseDto getBalance(Integer accountId) {
        Account account = getAccount(accountId);
        return AccountMapper.INSTANCE.toBalanceResponseDto(account);
    }

    @Override
    @Transactional
    public AccountDto exchange(Integer sourceAccountId, ExchangeRequest request) {
        Account sourceAccount = getAccount(sourceAccountId);
        Account targetAccount = getAccount(request.getTargetAccountId());

        if (sourceAccount.getId().equals(targetAccount.getId())) {
            throw new IllegalArgumentException("Source and target accounts cannot be the same");
        }

        if (!sourceAccount.getUserId().equals(targetAccount.getUserId())) {
            throw new IllegalArgumentException("Accounts must belong to the same user");
        }

        if (sourceAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException();
        }

        if (sourceAccount.getCurrency().equals(targetAccount.getCurrency())) {
            throw new IllegalArgumentException("Source and target accounts must have different currencies");
        }

        BigDecimal convertedAmount = ExchangeHelper.convert(request.getAmount(), sourceAccount.getCurrency(), targetAccount.getCurrency());
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(request.getAmount()));
        targetAccount.setBalance(targetAccount.getBalance().add(convertedAmount));
        accountRepository.saveAll(List.of(sourceAccount, targetAccount));

        saveTransaction(
                sourceAccount,
                TransactionType.EXCHANGE_OUT,
                request.getAmount()
        );

        saveTransaction(
                targetAccount,
                TransactionType.EXCHANGE_IN,
                convertedAmount
        );

        return AccountMapper.INSTANCE.toDto(sourceAccount);
    }

    @Override
    public TransactionHistoryDto getTransactionHistory(Integer accountId) {
        Account account = getAccount(accountId);
        List<AccountTransaction> accountTransactionsList = accountTransactionRepository.findByAccountIdOrderByCreatedOnDesc(account.getId());
        List<AccountTransactionDto> transactionHistoryDtoList = AccountTransactionMapper.INSTANCE.toDtoList(accountTransactionsList);
        TransactionHistoryDto transactionHistoryDto = new TransactionHistoryDto();
        transactionHistoryDto.setAccountName(account.getName());
        transactionHistoryDto.setAccountTransactionList(transactionHistoryDtoList);
        return transactionHistoryDto;
    }

    private Account getAccount(Integer accountId) {
        return accountRepository.findByIdAndUserId(accountId, userHelper.getCurrentUser().getId())
                .orElseThrow(AccountNotFoundException::new);
    }

    private void saveTransaction(
            Account account,
            TransactionType transactionType,
            BigDecimal amount
    ) {
        AccountTransaction transaction = new AccountTransaction();
        transaction.setAccountId(account.getId());
        transaction.setTransactionType(transactionType);
        transaction.setAmount(amount);
        transaction.setCurrency(account.getCurrency());
        transaction.setBalanceAfter(account.getBalance());
        accountTransactionRepository.save(transaction);
    }

}
