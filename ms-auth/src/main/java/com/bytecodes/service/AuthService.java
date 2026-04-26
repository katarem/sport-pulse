package com.bytecodes.service;

import com.bytecodes.entity.CreateUser;
import com.bytecodes.model.User;
import com.bytecodes.model.UserToken;
import com.bytecodes.response.ValidationResponse;

public interface AuthService {
    User register(CreateUser user);
    UserToken login(String email, String password);
    ValidationResponse validateUser(String token);
}
