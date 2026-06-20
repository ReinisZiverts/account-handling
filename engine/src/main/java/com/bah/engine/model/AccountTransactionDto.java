package com.bah.engine.model;

import com.bah.engine.enums.Currency;
import com.bah.engine.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountTransactionDto {

    private TransactionType transactionType;
    private BigDecimal amount;
    private Currency currency;
    private BigDecimal balanceAfter;

}
