package com.exercise.quotes.controller;


import com.exercise.quotes.dto.ItemDTO;
import com.exercise.quotes.dto.QuoteDTO;
import com.exercise.quotes.exception.QuoteException;
import com.exercise.quotes.service.IQuotes;
import com.exercise.quotes.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Set;

import static com.exercise.quotes.utils.ApiQuotesConstants.*;
import static com.exercise.quotes.utils.ErrorMessageQuotesConstant.ALREADY_EXIST_QUOTE;

@RestController
public class QuotesController {

    @Autowired
    IQuotes quotesService;
    @Autowired
    ItemService itemService;

    @PostMapping(ADD_QUOTE)
    QuoteDTO addQuotes(@Valid @RequestBody QuoteDTO quoteDTO) {
        QuoteDTO quote;
        try {
            quote =  quotesService.createQuote(quoteDTO);
        } catch (DataIntegrityViolationException e) {
            throw new QuoteException(ALREADY_EXIST_QUOTE, HttpStatus.BAD_REQUEST.value());

        }
        return quote;
    }


    @GetMapping(GET_ALL_QUOTES)
    List<QuoteDTO> getQuotes() {
        return quotesService.getAllQuotes();
    }


    @GetMapping(GET_QUOTE)
    QuoteDTO getQuote(@PathVariable String name) {
        return quotesService.getQuote(name);
    }


    @DeleteMapping(DELETE_QUOTE)
    void deleteQuote(@PathVariable long id) {
        quotesService.deleteQuote(id);
    }


    @PutMapping(UPDATE_QUOTE)
    QuoteDTO updateQuote(@Valid @PathVariable long id, @RequestBody QuoteDTO quoteDTO) {

        QuoteDTO quote;
        try {
            quote =  quotesService.updateQuote(id, quoteDTO);
        } catch (DataIntegrityViolationException e) {
            throw new QuoteException(ALREADY_EXIST_QUOTE, HttpStatus.BAD_REQUEST.value());

        }
        return quote;
    }

    @PostMapping(ADD_ITEM)
    public ItemDTO createItem(@PathVariable long idQuote, @RequestBody ItemDTO itemdto) {
        return itemService.createItem(idQuote, itemdto);
    }


    @PutMapping(UPDATE_ITEM)
    public ItemDTO updateItem(@RequestBody ItemDTO itemDto) {
        return itemService.updateItem(itemDto);
    }

    @DeleteMapping(DELETE_ITEM)
    public void deleteItem(@PathVariable long id) {
        itemService.deleteItem(id);
    }

    @GetMapping(GET_ALL_ITEMS)
    public Set<ItemDTO> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping(GET_ITEM)
    public ItemDTO getItems(@PathVariable long id) {
        return itemService.getItems(id);
    }


}
