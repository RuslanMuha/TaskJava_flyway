package com.exercise.quotes.utils;

import com.exercise.quotes.entity.ItemQuote;
import com.exercise.quotes.entity.Quote;
import com.exercise.quotes.dto.ItemDTO;
import com.exercise.quotes.dto.QuoteDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class QuoteUtilService {

   public List<QuoteDTO> convertToListQuoteDTO(List<Quote> all) {
        return all.stream().map(this::convertToQuoteDTO).collect(Collectors.toList());
    }


    public  QuoteDTO convertToQuoteDTO(Quote quote) {

        return QuoteDTO.builder()
                .id(quote.getId())
                .items(convertToSetItemDTO(quote.getItems()))
                .price(quote.getPrice())
                .name(quote.getName())
                .build();
    }


    public  Quote convertToQuote(QuoteDTO quoteDTO) {

        return Quote.builder()
                .id(quoteDTO.getId())
                .price(quoteDTO.getPrice())
                .name(quoteDTO.getName())
                .items(new HashSet<>())
                .build();
    }


    public  ItemQuote convertToItemQuote(ItemDTO itemQuoteDTO) {
        return ItemQuote.builder()
                .id(itemQuoteDTO.getId())
                .name(itemQuoteDTO.getName())
                .build();
    }

    public Set<ItemDTO> convertToSetItemDTO(Set<ItemQuote> items) {
        return items.stream()
                .map(this::convertToItemDTO)
                .collect(Collectors.toSet());
    }

    public ItemDTO convertToItemDTO(ItemQuote itemQuote) {
        return ItemDTO.builder()
                .id(itemQuote.getId())
                .name(itemQuote.getName())
                .build();
    }




}
