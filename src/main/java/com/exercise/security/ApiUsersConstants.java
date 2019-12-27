package com.exercise.security;

public interface ApiUsersConstants {
    String SIGNIN = "/users/signin";
    String SIGNUP = "/users/signup";
    String DELETE_USER = "/users/{email}";
    String REFRESH_TOKEN = "/users/refresh";
    String H2_CONSOLE = "/h2-console/**/**";

}
