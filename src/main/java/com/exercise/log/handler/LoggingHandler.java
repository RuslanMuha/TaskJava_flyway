package com.exercise.log.handler;


import com.exercise.log.Operation;
import com.exercise.log.entity.QuoteLog;
import com.exercise.log.service.LoggingService;
import com.exercise.quotes.dto.ItemDTO;
import com.exercise.quotes.dto.QuoteDTO;
import com.exercise.quotes.exception.AbstractApiException;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


@Aspect
@Component
@Log4j
public class LoggingHandler {


    private LoggingService loggingService;

    @Autowired
    public LoggingHandler(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Pointcut("within(com.exercise.quotes.controller.*)")
    public void inQuotesPackage() {
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {
    }


    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    protected void updateMethod() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    protected void addMethod() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    protected void deleteMethod() {
    }

    @Pointcut("addMethod() || deleteMethod() || updateMethod()")
    protected void cudMethods() {
    }

    @SneakyThrows
    @AfterThrowing(pointcut = "controller() && cudMethods() && inQuotesPackage()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, AbstractApiException exception) {
        long id = getId(joinPoint);


        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();



        loggingService.save(QuoteLog.builder()
                .quoteId(id)
                .createdDate(LocalDateTime.now())
                .errorCode(exception.getErrorCode())
                .message(exception.getMessage())
                .operation(Operation.getOperation(request.getMethod()))
                .build());

        log.error(" class name: " + joinPoint.getSignature().getDeclaringTypeName()
                + " http method: "
                + Operation.getOperation(request.getMethod())
                + " request path: "
                + request.getServletPath()
                + " errorCode: "
                + exception.getErrorCode()
                + " message: "
                + exception.getMessage());

    }


    @Before(value = "controller() && cudMethods() && inQuotesPackage()")
    public void logBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        long id = getId(joinPoint);
        loggingService.save(QuoteLog.builder()
                .quoteId(id)
                .createdDate(LocalDateTime.now())
                .operation(Operation.getOperation(request.getMethod()))
                .build());


    }

    private long getId(JoinPoint joinPoint) {
        Object obj = joinPoint.getArgs()[0];
        long id = 0;
        if (obj instanceof QuoteDTO) {
            QuoteDTO q = (QuoteDTO) joinPoint.getArgs()[0];
            id = q.getId();
        } else if (obj instanceof ItemDTO) {
            ItemDTO q = (ItemDTO) joinPoint.getArgs()[0];
            id = q.getId();
        } else if (obj instanceof Long) {
            id = (long) joinPoint.getArgs()[0];
        }
        return id;
    }

}
