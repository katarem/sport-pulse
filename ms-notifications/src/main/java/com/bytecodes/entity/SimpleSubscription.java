package com.bytecodes.entity;

import com.bytecodes.model.NotificationChannel;
import com.bytecodes.model.NotificationEvent;
import com.bytecodes.model.SubscriptionStatus;
import com.bytecodes.model.SubscriptionType;

import java.util.Set;
import java.util.UUID;

public interface SimpleSubscription {
    UUID getId();
    UUID getUserId();
    SubscriptionType getSubscriptionType();
    Integer getTeamId();
    Integer getFixtureId();
    Set<NotificationEvent> getEvents();
    NotificationChannel getChannel();
    String getWebhookUrl();
    SubscriptionStatus getStatus();
}
