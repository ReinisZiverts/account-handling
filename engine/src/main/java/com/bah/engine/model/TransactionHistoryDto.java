package com.bah.engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TransactionHistoryDto {

    private String accountName;
    @JsonProperty("accountTransactions")
    List<AccountTransactionDto> accountTransactionList;

}
