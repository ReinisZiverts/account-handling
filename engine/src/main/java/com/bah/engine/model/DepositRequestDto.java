package com.bah.engine.model;

import com.bah.engine.enums.Currency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositRequestDto {

    private Integer accountId;
    private BigDecimal amount;
    private Currency currency;

}
