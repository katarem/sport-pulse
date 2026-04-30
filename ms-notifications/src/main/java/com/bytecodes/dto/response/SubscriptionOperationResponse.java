package com.bytecodes.dto.response;

import com.bytecodes.model.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SubscriptionOperationResponse {
    private UUID subscriptionId;
    private SubscriptionStatus status;
    private Instant cancelledAt;
}
