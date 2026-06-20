package com.bah.engine.service;

import com.bah.engine.entity.Account;
import com.bah.engine.entity.AccountTransaction;
import com.bah.engine.entity.mapper.AccountMapper;
import com.bah.engine.entity.mapper.AccountTransactionsMapper;
import com.bah.engine.enums.TransactionType;
import com.bah.engine.helper.ExchangeHelper;
import com.bah.engine.model.*;
import com.bah.engine.repository.AccountRepository;
import com.bah.engine.repository.AccountTransactionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountTransactionsRepository accountTransactionsRepository;
    private final RestClient restClient = RestClient.create();

    @Override
    public AccountDto createAccount(CreateAccountRequest request) {

        Account account = new Account();
        account.setUserId(request.getUserId());
        account.setName(request.getName());
        account.setCurrency(request.getCurrency());

        accountRepository.save(account);
        return AccountMapper.INSTANCE.toDto(account);
    }

    @Override
    public AccountDto deposit(DepositRequestDto request) {
        Account account = accountRepository.findById(request.getAccountId()).orElseThrow();
        if (account.getCurrency() != request.getCurrency()) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepository.save(account);
        saveTransaction(account, TransactionType.DEPOSIT, request.getAmount());
        return AccountMapper.INSTANCE.toDto(account);
    }

    @Override
    public AccountDto debit(Integer accountId, DebitRequestDto request) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        if (account.getCurrency() != request.getCurrency()) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        simulateExternalDebitLogging();

        account.setBalance(account.getBalance().subtract(request.getAmount()));
        accountRepository.save(account);
        saveTransaction(account, TransactionType.DEBIT, request.getAmount());
        return AccountMapper.INSTANCE.toDto(account);
    }

    @Override
    public AccountDto getBalance(Integer accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        return AccountMapper.INSTANCE.toDto(account);
    }

    @Override
    public AccountDto exchange(Integer sourceAccountId, ExchangeRequest request) {
        Account sourceAccount = accountRepository.findById(sourceAccountId).orElseThrow();
        Account targetAccount = accountRepository.findById(request.getTargetAccountId()).orElseThrow();

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }

        if (sourceAccount.getId().equals(targetAccount.getId())) {
            throw new IllegalArgumentException("Source and target accounts cannot be the same");
        }

        if (!sourceAccount.getUserId().equals(targetAccount.getUserId())) {
            throw new IllegalArgumentException("Accounts must belong to the same user");
        }

        if (sourceAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
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
        List<AccountTransaction> accountTransactionsList = accountTransactionsRepository.findByAccountId(accountId);
        List<AccountTransactionDto> transactionHistoryDtoList = AccountTransactionsMapper.INSTANCE.toDtoList(accountTransactionsList);
        TransactionHistoryDto transactionHistoryDto = new TransactionHistoryDto();
        transactionHistoryDto.setAccountName(accountRepository.findById(accountId).orElseThrow().getName());
        transactionHistoryDto.setAccountTransactionList(transactionHistoryDtoList);
        return transactionHistoryDto;
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
        accountTransactionsRepository.save(transaction);
    }

    private void simulateExternalDebitLogging() {
        restClient
                .get()
                .uri("https://tools-httpstatus.pickup-services.com/200")
                .retrieve()
                .toBodilessEntity();
    }
}
