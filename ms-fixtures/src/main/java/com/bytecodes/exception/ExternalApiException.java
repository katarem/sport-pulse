package com.bytecodes.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ExternalApiException extends RuntimeException {

    private final Map<String, String> errors;

    @SuppressWarnings("unchecked")
    public ExternalApiException(Object errors) {
        if(!(errors instanceof Map)) {
            throw new UnknownFormatException();
        }
        this.errors = (Map<String, String>) errors;
    }

}
