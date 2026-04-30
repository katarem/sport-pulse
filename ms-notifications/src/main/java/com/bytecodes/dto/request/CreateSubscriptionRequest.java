package com.bytecodes.dto.request;

import com.bytecodes.model.NotificationChannel;
import com.bytecodes.model.NotificationEvent;
import com.bytecodes.model.SubscriptionType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class CreateSubscriptionRequest {
    private SubscriptionType type;
    private Integer fixtureId;
    @NotNull
    @NotEmpty
    private Set<NotificationEvent> events;
    private NotificationChannel channel;
    private String webhookUrl;
}
