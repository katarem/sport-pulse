package com.bytecodes.validator;

import com.bytecodes.dto.request.CreateSubscriptionRequest;
import com.bytecodes.entity.SimpleSubscription;
import com.bytecodes.exception.InvalidSubscriptionException;
import com.bytecodes.exception.SubscriptionAlreadyExistsException;
import com.bytecodes.model.ApiUser;
import com.bytecodes.model.NotificationChannel;
import com.bytecodes.model.SubscriptionStatus;
import com.bytecodes.model.SubscriptionType;
import com.bytecodes.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class SubscriptionValidator {

    private final SubscriptionRepository repository;

    private record TypeValidation(String field, Optional<?> value, SubscriptionType type){}
    private record ChannelValidation(String field, Optional<?> value, NotificationChannel channel){}

    public void validate(CreateSubscriptionRequest request, ApiUser user) throws InvalidSubscriptionException, SubscriptionAlreadyExistsException {
        validateType(request);
        validateChannel(request);
        checkConflict(request, user);
    }

    private void validateType(CreateSubscriptionRequest request) throws InvalidSubscriptionException {
        final TypeValidation businessValidation = switch (request.getType()) {
            case TEAM -> new TypeValidation("teamId", Optional.ofNullable(request.getTeamId()), SubscriptionType.TEAM);
            case FIXTURE -> new TypeValidation("fixtureId", Optional.ofNullable(request.getFixtureId()), SubscriptionType.FIXTURE);
        };
        if(businessValidation.value().isEmpty())
            throw new InvalidSubscriptionException(businessValidation.field(), businessValidation.type().name());
    }

    private void validateChannel(CreateSubscriptionRequest request) throws InvalidSubscriptionException {
        final Optional<ChannelValidation> businessValidation = switch (request.getChannel()) {
            case WEBHOOK -> Optional.of(new ChannelValidation("webhookUrl", Optional.ofNullable(request.getWebhookUrl()), NotificationChannel.WEBHOOK));
            case LOG -> Optional.ofNullable(null);
        };
        if(businessValidation.isPresent() && businessValidation.get().value().isEmpty()) {
            throw new InvalidSubscriptionException(businessValidation.get().field(), businessValidation.get().channel().name());
        }

    }

    private void checkConflict(CreateSubscriptionRequest request, ApiUser user) throws SubscriptionAlreadyExistsException {
        var existingSubscriptions = repository.findAllByUserIdAndStatus(user.getUserId(), SubscriptionStatus.ACTIVE);

        final boolean requestExistsAlready = switch (request.getType()) {
            case TEAM -> existingSubscriptions.stream().filter(sub -> sub.getType().equals(SubscriptionType.TEAM)).anyMatch(teamSubscriptionExists(request));
            case FIXTURE -> existingSubscriptions.stream().filter(sub -> sub.getType().equals(SubscriptionType.FIXTURE)).anyMatch(fixtureSubscriptionExists(request));
        };

        if(requestExistsAlready)
            throw new SubscriptionAlreadyExistsException();
    }

    private Predicate<SimpleSubscription> teamSubscriptionExists(CreateSubscriptionRequest newSubscription) {
        return existing -> existing.getTeamId().equals(newSubscription.getTeamId()) &&
                existing.getChannel().equals(newSubscription.getChannel());
    }

    private Predicate<SimpleSubscription> fixtureSubscriptionExists(CreateSubscriptionRequest newSubscription) {
        return existing -> existing.getFixtureId().equals(newSubscription.getFixtureId()) &&
                existing.getChannel().equals(newSubscription.getChannel());
    }

}
