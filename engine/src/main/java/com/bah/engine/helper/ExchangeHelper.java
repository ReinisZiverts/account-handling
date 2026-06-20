package com.bah.engine.helper;

import com.bah.engine.enums.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class ExchangeHelper {

    private ExchangeHelper() {}

    private static final Map<Currency, BigDecimal> EUR_EXCHANGE_RATES = Map.of(
            Currency.EUR, BigDecimal.valueOf(1.00),
            Currency.USD, BigDecimal.valueOf(1.1486),
            Currency.GBP, BigDecimal.valueOf(0.86695),
            Currency.SEK, BigDecimal.valueOf(10.9838),
            Currency.VND, BigDecimal.valueOf(30165.00)
    );

    public static BigDecimal convert(BigDecimal amount, Currency sourceCurrency, Currency targetCurrency) {

        BigDecimal sourceRate = EUR_EXCHANGE_RATES.get(sourceCurrency);
        BigDecimal targetRate = EUR_EXCHANGE_RATES.get(targetCurrency);

        BigDecimal amountInEur = amount.divide(sourceRate, 8, RoundingMode.HALF_UP);

        return amountInEur.multiply(targetRate).setScale(4, RoundingMode.HALF_UP);
    }
}
