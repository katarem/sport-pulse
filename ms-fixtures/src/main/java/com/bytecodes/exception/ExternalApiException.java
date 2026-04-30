package com.bytecodes.exception;

public class ExternalApiException extends RuntimeException {

    @SuppressWarnings("unchecked")
    public ExternalApiException(String message) {
        super(message);    }

}
