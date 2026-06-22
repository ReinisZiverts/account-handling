package com.bah.engine.model;

import com.bah.engine.enums.Currency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {

    private Integer id;
    private BigDecimal balance;
    private Currency currency;
    private String name;

}
