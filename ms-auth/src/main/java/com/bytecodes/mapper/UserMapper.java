package com.bytecodes.mapper;

import com.bytecodes.entity.CreateUser;
import com.bytecodes.entity.SecurityUser;
import com.bytecodes.entity.UserEntity;
import com.bytecodes.entity.ValidationUser;
import com.bytecodes.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toModel(UserEntity entity);

    SecurityUser toSecurityUser(UserEntity entity);

    UserEntity toEntity(CreateUser user);

    ValidationUser toValidationUser(UserEntity entity);

}
