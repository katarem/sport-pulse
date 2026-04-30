package com.bytecodes.mapper;

import com.bytecodes.dto.request.CreateSubscriptionRequest;
import com.bytecodes.entity.SimpleSubscription;
import com.bytecodes.entity.SubscriptionEntity;
import com.bytecodes.model.Subscription;
import com.bytecodes.model.SubscriptionList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubscriptionMapper {

    @Mapping(target = "subscriptionId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Subscription toModel(CreateSubscriptionRequest dto);

    Set<SubscriptionList> toModel(Set<SimpleSubscription> dto);

    @Mapping(target = "subscriptionId", source = "id")
    SubscriptionList toModel(SimpleSubscription dto);

    @Mapping(target = "subscriptionId", source = "id")
    Subscription toModel(SubscriptionEntity entity);

    @Mapping(target = "id", source = "subscriptionId")
    SubscriptionEntity toEntity(Subscription model);
}
