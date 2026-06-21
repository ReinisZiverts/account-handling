package com.bah.engine.model;

import com.bah.engine.enums.Currency;
import com.bah.engine.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountTransactionDto {

    private TransactionType transactionType;
    private BigDecimal amount;
    private Currency currency;
    private BigDecimal balanceAfter;
    private LocalDateTime createdOn;

}
