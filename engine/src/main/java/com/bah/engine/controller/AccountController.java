package com.bah.engine.controller;

import com.bah.engine.model.*;
import com.bah.engine.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping()
    public ResponseEntity<AccountDto> createAccount(@RequestBody CreateAccountRequest request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<AccountDto> deposit(@RequestBody DepositRequestDto request) {
        return ResponseEntity.ok(accountService.deposit(request));
    }

    @PostMapping("/{accountId}/debit")
    public ResponseEntity<AccountDto> debit(@PathVariable Integer accountId, @RequestBody DebitRequestDto request) {
        return ResponseEntity.ok(accountService.debit(accountId, request));
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<AccountDto> getBalance(@PathVariable Integer accountId) {
        return ResponseEntity.ok(accountService.getBalance(accountId));
    }

    @PostMapping("/{sourceAccountId}/exchange")
    public ResponseEntity<AccountDto> exchange(@PathVariable Integer sourceAccountId, @RequestBody ExchangeRequest request) {
        return ResponseEntity.ok(accountService.exchange(sourceAccountId, request));
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<TransactionHistoryDto> getTransactionHistory(@PathVariable Integer accountId) {
        return ResponseEntity.ok(accountService.getTransactionHistory(accountId));
    }

}
