package com.exercise.quotes.service;


import com.exercise.quotes.dto.QuoteDTO;

import java.util.List;

public interface IQuotes {

    QuoteDTO createQuote(QuoteDTO quoteDto);
    void deleteQuote(long id);
    List<QuoteDTO> getAllQuotes();
    QuoteDTO getQuote(String name);
    QuoteDTO updateQuote(long id,QuoteDTO quoteDTO);
}
