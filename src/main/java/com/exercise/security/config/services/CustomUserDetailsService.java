package com.exercise.security.config.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.exercise.security.ErrorMessageUsersConstant.USER_NOT_FOUND;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private UserServiceCheckJpa userService;

    @Autowired
    public CustomUserDetailsService(UserServiceCheckJpa userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userService.getUser(username).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }
}
