package com.bah.engine.service;


import com.bah.engine.model.*;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(CreateAccountRequest request);

    List<AccountDto> getAllUserAccounts();

    AccountDto getUserAccount(Integer accountId);

    AccountDto deposit(Integer accountId, DepositRequestDto request);

    AccountDto debit(Integer accountId, DebitRequestDto request);

    BalanceResponseDto getBalance(Integer accountId);

    AccountDto exchange(Integer sourceAccountId, ExchangeRequest request);

    TransactionHistoryDto getTransactionHistory(Integer accountId, Integer page, Integer size);

    AccountTransactionDto getTransactionInformation(Integer accountId, Integer transactionId);
}
