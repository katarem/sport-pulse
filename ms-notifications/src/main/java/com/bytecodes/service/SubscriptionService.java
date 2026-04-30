package com.bytecodes.service;

import com.bytecodes.dto.request.CreateSubscriptionRequest;
import com.bytecodes.dto.response.SubscriptionOperationResponse;
import com.bytecodes.model.ApiUser;
import com.bytecodes.model.Subscription;
import com.bytecodes.model.SubscriptionList;

import java.util.Set;
import java.util.UUID;

public interface SubscriptionService {
    Subscription subscribe(CreateSubscriptionRequest request, ApiUser user);
    Set<SubscriptionList> getSubscriptions(ApiUser user);
    SubscriptionOperationResponse unsubscribe(UUID subscriptionId, ApiUser user);
}