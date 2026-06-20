package com.bah.engine.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeRequest {

    private Integer targetAccountId;
    private BigDecimal amount;

}
