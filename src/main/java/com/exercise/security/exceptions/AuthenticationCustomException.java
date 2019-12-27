package com.exercise.security.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthenticationCustomException extends RuntimeException {
    private int errorCode;

    public AuthenticationCustomException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;

    }
}
