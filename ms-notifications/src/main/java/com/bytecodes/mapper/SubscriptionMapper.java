package com.bytecodes.mapper;

import com.bytecodes.dto.request.CreateSubscriptionRequest;
import com.bytecodes.entity.SimpleSubscription;
import com.bytecodes.entity.SubscriptionEntity;
import com.bytecodes.model.Subscription;
import com.bytecodes.model.SubscriptionList;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubscriptionMapper {

    Subscription toModel(CreateSubscriptionRequest dto);

    Set<SubscriptionList> toModel(Set<SimpleSubscription> dto);

    Subscription toModel(SubscriptionEntity entity);

    SubscriptionEntity toEntity(Subscription model);
}
