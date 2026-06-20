package com.bah.engine.model;

import com.bah.engine.enums.Currency;
import lombok.Data;

@Data
public class CreateAccountRequest {

    private Integer userId;
    private String name;
    private Currency currency;

}
