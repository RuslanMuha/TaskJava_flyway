package com.exercise.security.controller;


import com.exercise.security.dto.UserRequestDto;
import com.exercise.security.entity.UserCustom;
import com.exercise.security.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.exercise.security.ApiUsersConstants.*;

@RestController
public class UserController {


    private UserService userService;
    private ObjectMapper objectMapper;

    @Autowired
    public UserController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(SIGNIN)
    public String login(@RequestBody UserRequestDto user) {
        return userService.signin(objectMapper.convertValue(user, UserCustom.class));
    }

    @PostMapping(SIGNUP)
    public void signup(@RequestBody UserRequestDto user) {
        userService.signup(objectMapper.convertValue(user, UserCustom.class));
    }


    @DeleteMapping(DELETE_USER)
    public String delete(@PathVariable String email) {
        userService.deleteUser(email);
        return email;
    }

    @GetMapping(REFRESH_TOKEN)
    public String refresh(HttpServletRequest req) {
        return userService.refreshToken(req.getRemoteUser());
    }
}
