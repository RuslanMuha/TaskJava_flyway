package com.exercise.security.service;

import com.exercise.security.entity.UserCustom;

public interface UserService {
    String signin(UserCustom user);

    void signup(UserCustom user);

    void deleteUser(String email);
    String refreshToken(String email);
}
