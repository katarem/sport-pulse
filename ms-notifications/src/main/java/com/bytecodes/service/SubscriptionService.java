package com.bytecodes.service;

import com.bytecodes.dto.request.CreateSubscriptionRequest;
import com.bytecodes.dto.response.SubscriptionOperationResponse;
import com.bytecodes.exception.InvalidAccessException;
import com.bytecodes.exception.InvalidSubscriptionException;
import com.bytecodes.exception.SubscriptionAlreadyExistsException;
import com.bytecodes.exception.SubscriptionNotFoundException;
import com.bytecodes.model.ApiUser;
import com.bytecodes.model.Subscription;
import com.bytecodes.model.SubscriptionList;

import java.util.Set;
import java.util.UUID;

public interface SubscriptionService {
    Subscription subscribe(CreateSubscriptionRequest request, ApiUser user) throws InvalidSubscriptionException, SubscriptionAlreadyExistsException;
    Set<SubscriptionList> getSubscriptions(ApiUser user);
    SubscriptionOperationResponse unsubscribe(UUID subscriptionId, ApiUser user) throws InvalidAccessException, SubscriptionNotFoundException;
}