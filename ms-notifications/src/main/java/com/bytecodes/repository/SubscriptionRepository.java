package com.bytecodes.repository;

import com.bytecodes.entity.SimpleSubscription;
import com.bytecodes.entity.SubscriptionEntity;
import com.bytecodes.model.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, UUID> {
    Set<SimpleSubscription> getSubscriptionsByUserIdAndStatus(UUID userId, SubscriptionStatus status);
    long deleteByIdAndUserId(UUID id, UUID userId);
}
