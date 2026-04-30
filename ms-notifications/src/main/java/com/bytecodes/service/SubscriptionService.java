package com.bytecodes.service;

import com.bytecodes.dto.request.CreateSubscriptionRequest;
import com.bytecodes.model.ApiUser;
import com.bytecodes.model.Subscription;

import java.util.Set;
import java.util.UUID;

public interface SubscriptionService {
    Subscription subscribe(CreateSubscriptionRequest request, ApiUser user);
    Set<Subscription> getSubscriptions(ApiUser user);
    void unsubscribe(UUID subscriptionId);
}