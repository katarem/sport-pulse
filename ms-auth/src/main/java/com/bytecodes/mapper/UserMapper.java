package com.bytecodes.mapper;

import com.bytecodes.entity.CreateUser;
import com.bytecodes.entity.SecurityUser;
import com.bytecodes.entity.UserEntity;
import com.bytecodes.response.ValidationResponse;
import com.bytecodes.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toModel(UserEntity entity);

    SecurityUser toSecurityUser(UserEntity entity);

    UserEntity toEntity(CreateUser user);

    @Mapping(source = "id", target = "userId")
    @Mapping(target = "valid", constant = "true")
    ValidationResponse toValidationUser(UserEntity entity);

}
