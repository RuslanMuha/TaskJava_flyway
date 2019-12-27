package com.exercise.quotes.handler;

import com.exercise.quotes.dto.ErrorResponse;
import com.exercise.quotes.exception.AbstractApiException;
import com.exercise.quotes.utils.LevelError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionsHandlerQuotesController extends ResponseEntityExceptionHandler {


    @ExceptionHandler(AbstractApiException.class)
    public  ResponseEntity<Object> handleAbstractApiException(AbstractApiException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .description(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .level(LevelError.ERROR).build();

        return ResponseEntity.status(ex.getErrorCode()).body(response);
    }



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(ErrorResponse.builder()
        .description(details.toString())
        .errorCode(status.value())
        .level(LevelError.WARNING).build());
    }
}
