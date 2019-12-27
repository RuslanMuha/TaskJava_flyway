package com.exercise.quotes.repository;


import com.exercise.quotes.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    Optional<Quote> findByName(String name);

}
