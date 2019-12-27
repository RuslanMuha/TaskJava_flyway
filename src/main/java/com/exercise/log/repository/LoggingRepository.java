package com.exercise.log.repository;

import com.exercise.log.entity.QuoteLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggingRepository extends JpaRepository<QuoteLog, Long> {

}
