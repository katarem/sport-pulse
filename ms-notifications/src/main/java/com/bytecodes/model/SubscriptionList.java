package com.bytecodes.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionList {
    private UUID subscriptionId;
    private SubscriptionType type;
    private Integer teamId;
    private Integer fixtureId;
    private Set<NotificationEvent> events;
    private NotificationChannel channel;
    private String webhookUrl;
    private SubscriptionStatus status;
}