package com.bytecodes.exception;

import java.util.UUID;

public class SubscriptionNotFoundException extends Throwable {

    public SubscriptionNotFoundException(UUID subscriptionId) {
        super("La subscripción con ID " + subscriptionId + " no existe");
    }

}
