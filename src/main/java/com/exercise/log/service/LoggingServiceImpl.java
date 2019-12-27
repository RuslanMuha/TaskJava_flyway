package com.exercise.log.service;

import com.exercise.log.entity.QuoteLog;
import com.exercise.log.repository.LoggingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoggingServiceImpl implements LoggingService {


    private LoggingRepository loggingRepository;

    @Autowired
    public LoggingServiceImpl(LoggingRepository loggingRepository) {
        this.loggingRepository = loggingRepository;
    }

    @Override
    public void save(QuoteLog log) {
        loggingRepository.save(log);
    }
}
