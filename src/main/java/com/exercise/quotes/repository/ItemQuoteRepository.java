package com.exercise.quotes.repository;

import com.exercise.quotes.entity.ItemQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;


public interface ItemQuoteRepository extends JpaRepository<ItemQuote, Long> {

}
