package com.exercise.security.config.services;

import com.exercise.security.entity.UserCustom;

import java.util.Optional;

public interface UserServiceCheckJpa {

    Optional<UserCustom> getUser(String username);
}
