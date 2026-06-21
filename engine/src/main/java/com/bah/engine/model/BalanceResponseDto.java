package com.bah.engine.model;

import com.bah.engine.enums.Currency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceResponseDto {

    private BigDecimal balance;
    private Currency currency;

}
