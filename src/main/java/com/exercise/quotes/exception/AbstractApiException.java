package com.exercise.quotes.exception;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AbstractApiException extends RuntimeException {
    private int errorCode;


    public AbstractApiException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }


}
