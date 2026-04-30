package com.bytecodes.repository;

import com.bytecodes.model.NotificationChannel;
import com.bytecodes.model.NotificationEvent;
import com.bytecodes.model.SubscriptionStatus;
import com.bytecodes.model.SubscriptionType;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * Projection for {@link com.bytecodes.entity.SubscriptionEntity}
 */
public interface SubscriptionEntityInfo {
    UUID getId();

    UUID getUserId();

    SubscriptionType getType();

    Integer getTeamId();

    Integer getFixtureId();

    Set<NotificationEvent> getEvents();

    NotificationChannel getChannel();

    String getWebhookUrl();

    SubscriptionStatus getStatus();

    Instant getCreatedAt();
}