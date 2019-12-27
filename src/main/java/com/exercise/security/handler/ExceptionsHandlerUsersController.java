package com.exercise.security.handler;

import com.exercise.security.exceptions.AuthenticationCustomException;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

import static com.exercise.security.ErrorMessageUsersConstant.ACCESS_DENIED;


@ControllerAdvice
public class ExceptionsHandlerUsersController {

    @SneakyThrows
    @ExceptionHandler(AuthenticationCustomException.class)
    public void handleCustomException(HttpServletResponse res, AuthenticationCustomException ex)  {
        res.sendError(ex.getErrorCode(), ex.getMessage());
    }
    @SneakyThrows
    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(HttpServletResponse res)  {
        res.sendError(HttpStatus.FORBIDDEN.value(), ACCESS_DENIED);
    }


    @SneakyThrows
    @ExceptionHandler(UsernameNotFoundException.class)
    public void handleUserNotFoundException(HttpServletResponse res)  {
        res.sendError(HttpStatus.NOT_FOUND.value(), ACCESS_DENIED);
    }


}
