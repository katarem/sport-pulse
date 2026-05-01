package com.bytecodes.exception;

public class InvalidSubscriptionException extends Throwable {
    public InvalidSubscriptionException(String field, String type) {
        super(String.format("'%s' es requerido en el tipo '%s'", field, type));
    }
}
