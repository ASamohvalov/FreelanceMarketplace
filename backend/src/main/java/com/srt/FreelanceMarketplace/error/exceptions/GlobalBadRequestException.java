package com.srt.FreelanceMarketplace.error.exceptions;

public class GlobalBadRequestException extends RuntimeException {
    public GlobalBadRequestException(String message) {
        super(message);
    }
}
