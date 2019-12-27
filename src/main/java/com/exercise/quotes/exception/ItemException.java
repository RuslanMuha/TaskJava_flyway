package com.exercise.quotes.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ItemException extends AbstractApiException{


    public ItemException(String message, int errorCode) {
        super(message, errorCode);
    }
}
