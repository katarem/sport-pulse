package com.bytecodes.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Subscription {
    private UUID subscriptionId;
    private UUID userId;
    private SubscriptionType type;
    private Integer teamId;
    private Integer fixtureId;
    private Set<NotificationEvent> events;
    private NotificationChannel channel;
    private String webhookUrl;
    private SubscriptionStatus status;
    private Instant createdAt;
}
