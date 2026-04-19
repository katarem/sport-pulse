package com.bytecodes.exception;

public class UnknownAddressException extends RuntimeException {
    public UnknownAddressException() {
        super("La dirección IP es necesaria para usar esta api");
    }
}
