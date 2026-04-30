package com.bytecodes.dto.request;

import com.bytecodes.model.NotificationChannel;
import com.bytecodes.model.NotificationEvent;
import com.bytecodes.model.SubscriptionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class CreateSubscriptionRequest {
    @NotNull
    private SubscriptionType type;
    @NotNull
    @Min(1)
    private Integer teamId;
    @NotNull
    @NotEmpty
    private Set<NotificationEvent> events;
    @NotNull
    private NotificationChannel channel;
    @NotNull
    @NotEmpty
    private String webhookUrl;
}
