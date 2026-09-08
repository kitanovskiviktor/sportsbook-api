package com.sportsbook.exception;

public class OddsChangedException extends RuntimeException {
    public OddsChangedException(String message) {
        super(message);
    }
}
