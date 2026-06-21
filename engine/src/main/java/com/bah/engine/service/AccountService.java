package com.bah.engine.service;


import com.bah.engine.model.*;

public interface AccountService {

    AccountDto createAccount(CreateAccountRequest request);

    AccountDto deposit(Integer accountId, DepositRequestDto request);

    AccountDto debit(Integer accountId, DebitRequestDto request);

    BalanceResponseDto getBalance(Integer accountId);

    AccountDto exchange(Integer sourceAccountId, ExchangeRequest request);

    TransactionHistoryDto getTransactionHistory(Integer accountId);

}
