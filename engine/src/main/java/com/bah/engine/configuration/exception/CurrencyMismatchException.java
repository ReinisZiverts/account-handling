package com.bah.engine.configuration.exception;

public class CurrencyMismatchException extends RuntimeException {
    public CurrencyMismatchException() {
        super("Currency mismatch");
    }
}
