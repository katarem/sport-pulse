package com.bytecodes.service;

import com.bytecodes.entity.CreateUser;
import com.bytecodes.entity.ValidationUser;
import com.bytecodes.model.User;
import com.bytecodes.model.UserToken;

public interface AuthService {
    User register(CreateUser user);
    UserToken login(String email, String password);
    ValidationUser validateUser(String token);
}
