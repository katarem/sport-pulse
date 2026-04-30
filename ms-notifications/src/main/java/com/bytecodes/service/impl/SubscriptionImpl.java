package com.bytecodes.service.impl;

import com.bytecodes.dto.request.CreateSubscriptionRequest;
import com.bytecodes.dto.response.SubscriptionOperationResponse;
import com.bytecodes.entity.SimpleSubscription;
import com.bytecodes.entity.SubscriptionEntity;
import com.bytecodes.exception.SubscriptionAlreadyExistsException;
import com.bytecodes.exception.SubscriptionNotFoundException;
import com.bytecodes.mapper.SubscriptionMapper;
import com.bytecodes.model.ApiUser;
import com.bytecodes.model.Subscription;
import com.bytecodes.model.SubscriptionList;
import com.bytecodes.model.SubscriptionStatus;
import com.bytecodes.repository.SubscriptionRepository;
import com.bytecodes.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class SubscriptionImpl implements SubscriptionService {

    private final SubscriptionRepository repository;
    private final SubscriptionMapper mapper;

    @Override
    public Subscription subscribe(CreateSubscriptionRequest request, ApiUser user) {

        this.checkConflict(request, user);

        final var subscriptionModel = mapper.toModel(request);

        subscriptionModel.setCreatedAt(Instant.now());
        subscriptionModel.setUserId(user.getUserId());

        SubscriptionEntity subscriptionEntity = mapper.toEntity(subscriptionModel);
        subscriptionEntity = repository.save(subscriptionEntity);

        return mapper.toModel(subscriptionEntity);
    }

    private void checkConflict(CreateSubscriptionRequest request, ApiUser user) {
        var existingSubscriptions = repository.getSubscriptionsByUserIdAndStatus(user.getUserId(), SubscriptionStatus.ACTIVE);
        var requestExistsAlready = existingSubscriptions.stream()
                .anyMatch(subscriptionExists(request));

        if(requestExistsAlready)
            throw new SubscriptionAlreadyExistsException();
    }

    @Override
    public Set<SubscriptionList> getSubscriptions(ApiUser user) {
        var entities = repository.getSubscriptionsByUserIdAndStatus(user.getUserId(), SubscriptionStatus.ACTIVE);
        return mapper.toModel(entities);
    }

    @Override
    public SubscriptionOperationResponse unsubscribe(UUID subscriptionId, ApiUser user) {
        long deleted = repository.deleteByIdAndUserId(subscriptionId, user.getUserId());
        if(deleted != 1)
            throw new SubscriptionNotFoundException(subscriptionId);
        return new SubscriptionOperationResponse(subscriptionId, SubscriptionStatus.CANCELLED, Instant.now());
    }

    private Predicate<SimpleSubscription> subscriptionExists(CreateSubscriptionRequest newSubscription) {
        return existing -> existing.getTeamId().equals(newSubscription.getTeamId()) &&
                existing.getChannel().equals(newSubscription.getChannel());
    }
}
