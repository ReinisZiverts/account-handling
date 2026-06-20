package com.bah.engine.model;

import lombok.Data;

import java.util.List;

@Data
public class TransactionHistoryDto {

    private String accountName;
    List<AccountTransactionDto> accountTransactionList;

}
