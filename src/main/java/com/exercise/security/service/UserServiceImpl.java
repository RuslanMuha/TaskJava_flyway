package com.exercise.security.service;


import com.exercise.security.config.TokenHandler;
import com.exercise.security.entity.Role;
import com.exercise.security.entity.UserCustom;
import com.exercise.security.exceptions.AuthenticationCustomException;
import com.exercise.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.exercise.security.ErrorMessageUsersConstant.*;


@Service
public class UserServiceImpl implements UserService {

    @Value("${token.expires:60}")
    private long EXPIRES_MINUTE;


    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TokenHandler tokenHandler;

    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, TokenHandler tokenHandler) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenHandler = tokenHandler;
    }

    @Override
    public String signin(UserCustom user) {

        try {
            List<Role> authorities = userRepository.findByUsername(user.getUsername()).get().getAuthorities();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            return tokenHandler.generateAccessToken(user.getUsername(), authorities, LocalDateTime.now().plusMinutes(EXPIRES_MINUTE));
        } catch (Exception e) {
            throw new AuthenticationCustomException(HttpStatus.UNAUTHORIZED.value(), INVALID_USERNAME_OR_PASSWORD);
        }


    }

    @Transactional
    @Override
    public void signup(UserCustom user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled(true);
            user.setAccountNonExpired(true);
            user.setCredentialsNonExpired(true);
            user.setAccountNonLocked(true);
            userRepository.save(user);
        } else {
            throw new AuthenticationCustomException(HttpStatus.IM_USED.value(), USER_IN_USE);
        }
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        userRepository.deleteByUsername(email);
    }

    @Override
    public String refreshToken(String email) {
        UserCustom user = userRepository.findByUsername(email).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        return tokenHandler.generateAccessToken(email, user.getAuthorities(), LocalDateTime.now().plusHours(EXPIRES_MINUTE));
    }
}
