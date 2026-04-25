package com.bytecodes.service;

import com.bytecodes.entity.CreateUser;
import com.bytecodes.entity.ValidationUser;
import com.bytecodes.model.User;

public interface AuthService {
    User register(CreateUser user);
    User login(String email, String password);
    ValidationUser validateUser(String token);
}
