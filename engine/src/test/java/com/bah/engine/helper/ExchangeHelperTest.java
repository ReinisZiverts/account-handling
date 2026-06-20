package com.bah.engine.helper;

import com.bah.engine.enums.Currency;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExchangeHelperTest {

    @Test
    void convertSekToUsd() {
        BigDecimal amount = BigDecimal.valueOf(152.15);
        Currency sourceCurrency = Currency.SEK;
        Currency targetCurrency = Currency.USD;
        BigDecimal expectedExchangeValue = BigDecimal.valueOf(15.9107);
        BigDecimal actualConvertedValue = ExchangeHelper.convert(amount, sourceCurrency, targetCurrency);
        assertEquals(expectedExchangeValue, actualConvertedValue);
    }

    @Test
    void convertVndToUsd() {
        BigDecimal amount = BigDecimal.valueOf(10_000_000);
        Currency sourceCurrency = Currency.VND;
        Currency targetCurrency = Currency.USD;
        BigDecimal expectedExchangeValue = BigDecimal.valueOf(380.7724);
        BigDecimal actualConvertedValue = ExchangeHelper.convert(amount, sourceCurrency, targetCurrency);
        assertEquals(expectedExchangeValue, actualConvertedValue);
    }
}