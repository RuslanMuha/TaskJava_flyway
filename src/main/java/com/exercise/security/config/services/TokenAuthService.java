package com.exercise.security.config.services;

import com.exercise.security.config.TokenHandler;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class TokenAuthService {
    private static final String AUTH_HEADER_TOKEN = "X-Auth-Token";

    private TokenHandler tokenhandler;
    private UserServiceCheckJpaImpl userService;

    @Autowired
    public TokenAuthService(TokenHandler tokenhandler, UserServiceCheckJpaImpl userService) {
        this.tokenhandler = tokenhandler;
        this.userService = userService;
    }

    public Optional<Authentication> getAuthentication(@NonNull HttpServletRequest request) {

        return Optional.ofNullable(request.getHeader(AUTH_HEADER_TOKEN))
                .flatMap(tokenhandler::extractUserId)
                .flatMap(userService::getUser)
                .map(UserAuthentication::new);

    }
}
