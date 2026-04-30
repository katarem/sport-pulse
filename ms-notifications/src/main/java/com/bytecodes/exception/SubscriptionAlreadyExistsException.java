package com.bytecodes.exception;

public class SubscriptionAlreadyExistsException extends Throwable {

    public SubscriptionAlreadyExistsException() {
        super("Ya tienes una subscripción al mismo equipo y por el mismo canal.");
    }

}
