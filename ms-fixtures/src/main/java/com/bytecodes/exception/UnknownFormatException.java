package com.bytecodes.exception;

public class UnknownFormatException extends RuntimeException {
    public UnknownFormatException() {
        super("Formato no válido, se esperaban los errores en un map.");
    }
}
