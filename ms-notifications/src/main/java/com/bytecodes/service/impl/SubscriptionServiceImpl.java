package com.bytecodes.service.impl;

import com.bytecodes.dto.request.CreateSubscriptionRequest;
import com.bytecodes.dto.response.SubscriptionOperationResponse;
import com.bytecodes.exception.InvalidAccessException;
import com.bytecodes.entity.SubscriptionEntity;
import com.bytecodes.exception.InvalidSubscriptionException;
import com.bytecodes.exception.SubscriptionAlreadyExistsException;
import com.bytecodes.exception.SubscriptionNotFoundException;
import com.bytecodes.mapper.SubscriptionMapper;
import com.bytecodes.model.ApiUser;
import com.bytecodes.model.Subscription;
import com.bytecodes.model.SubscriptionList;
import com.bytecodes.model.SubscriptionStatus;
import com.bytecodes.repository.SubscriptionRepository;
import com.bytecodes.service.SubscriptionService;
import com.bytecodes.validator.SubscriptionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository repository;
    private final SubscriptionMapper mapper;
    private final SubscriptionValidator validator;

    @Override
    @Transactional
    public Subscription subscribe(CreateSubscriptionRequest request, ApiUser user) throws InvalidSubscriptionException, SubscriptionAlreadyExistsException {

        validator.validate(request, user);

        final var subscriptionModel = mapper.toModel(request);

        subscriptionModel.setCreatedAt(Instant.now());
        subscriptionModel.setUserId(user.getUserId());
        subscriptionModel.setStatus(SubscriptionStatus.ACTIVE);

        SubscriptionEntity subscriptionEntity = mapper.toEntity(subscriptionModel);
        subscriptionEntity = repository.save(subscriptionEntity);

        return mapper.toModel(subscriptionEntity);
    }



    @Override
    public Set<SubscriptionList> getSubscriptions(ApiUser user) {
        var entities = repository.findAllByUserIdAndStatus(user.getUserId(), SubscriptionStatus.ACTIVE);
        return mapper.toModel(entities);
    }

    @Override
    @Transactional
    public SubscriptionOperationResponse unsubscribe(UUID subscriptionId, ApiUser user) throws InvalidAccessException, SubscriptionNotFoundException {

        var existingSubscription = repository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionNotFoundException(subscriptionId));

        if(!existingSubscription.getUserId().equals(user.getUserId()))
            throw new InvalidAccessException();

        existingSubscription.setStatus(SubscriptionStatus.CANCELLED);

        return new SubscriptionOperationResponse(subscriptionId, SubscriptionStatus.CANCELLED, Instant.now());
    }


}
