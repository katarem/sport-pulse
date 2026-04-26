package com.bytecodes.exception;

public class UserTokenExpiredException extends RuntimeException {

    public UserTokenExpiredException() {
        super("El token ha expirado");
    }

}
