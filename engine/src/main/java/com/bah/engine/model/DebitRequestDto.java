package com.bah.engine.model;

import com.bah.engine.enums.Currency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DebitRequestDto {

    private BigDecimal amount;
    private Currency currency;

}
