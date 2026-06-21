package com.bah.engine.controller;

import com.bah.engine.model.*;
import com.bah.engine.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping()
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(request));
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<AccountDto> deposit(
            @PathVariable Integer accountId,
            @Valid @RequestBody DepositRequestDto request
    ) {
        return ResponseEntity.ok(accountService.deposit(accountId, request));
    }

    @PostMapping("/{accountId}/debit")
    public ResponseEntity<AccountDto> debit(
            @PathVariable Integer accountId,
            @Valid @RequestBody DebitRequestDto request
    ) {
        return ResponseEntity.ok(accountService.debit(accountId, request));
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BalanceResponseDto> getBalance(@PathVariable Integer accountId) {
        return ResponseEntity.ok(accountService.getBalance(accountId));
    }

    @PostMapping("/{sourceAccountId}/exchange")
    public ResponseEntity<AccountDto> exchange(
            @PathVariable Integer sourceAccountId,
            @Valid @RequestBody ExchangeRequest request
    ) {
        return ResponseEntity.ok(accountService.exchange(sourceAccountId, request));
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<TransactionHistoryDto> getTransactionHistory(@PathVariable Integer accountId) {
        return ResponseEntity.ok(accountService.getTransactionHistory(accountId));
    }

}
