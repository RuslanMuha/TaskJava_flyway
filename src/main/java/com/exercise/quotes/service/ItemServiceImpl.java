package com.exercise.quotes.service;


import com.exercise.quotes.dto.ItemDTO;
import com.exercise.quotes.entity.ItemQuote;
import com.exercise.quotes.entity.Quote;
import com.exercise.quotes.exception.ItemException;
import com.exercise.quotes.exception.QuoteException;
import com.exercise.quotes.repository.ItemQuoteRepository;
import com.exercise.quotes.repository.QuoteRepository;
import com.exercise.quotes.utils.QuoteUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static com.exercise.quotes.utils.ErrorMessageQuotesConstant.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Service
public class ItemServiceImpl implements ItemService {


    private QuoteUtilService quoteUtilService;

    private QuoteRepository quoteRepository;

    private ItemQuoteRepository itemQuoteRepository;

    @Autowired
    public ItemServiceImpl(QuoteUtilService quoteUtilService, QuoteRepository quoteRepository, ItemQuoteRepository itemQuoteRepository) {
        this.quoteUtilService = quoteUtilService;
        this.quoteRepository = quoteRepository;
        this.itemQuoteRepository = itemQuoteRepository;
    }

    @Transactional
    @Override
    public ItemDTO createItem(long idQuote, ItemDTO itemDTO) {
        Quote quote = ifNotExistQuoteThrow(idQuote);
        ifExistItemThrow(itemDTO.getId());
        ItemQuote itemQuote = quoteUtilService.convertToItemQuote(itemDTO);

        quote.getItems().add(itemQuote);
        itemQuoteRepository.save(itemQuote);
        return itemDTO;
    }


    @Transactional
    @Override
    public ItemDTO updateItem(ItemDTO itemDTO) {
        ItemQuote itemQuote = ifNotExistItemThrow(itemDTO.getId());
        itemQuote.setName(itemDTO.getName());
        return quoteUtilService.convertToItemDTO(itemQuote);
    }

    @Transactional
    @Override
    public void deleteItem(long id) {
        ifNotExistItemThrow(id);
        itemQuoteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<ItemDTO> getAllItems() {

        return quoteUtilService.convertToSetItemDTO(new HashSet<>(itemQuoteRepository.findAll()));
    }

    @Transactional(readOnly = true)
    @Override
    public ItemDTO getItems(long id) {
        return quoteUtilService.convertToItemDTO(ifNotExistItemThrow(id));

    }


    private Quote ifNotExistQuoteThrow(long idQuote) {
        return quoteRepository.findById(idQuote)
                .orElseThrow(() -> new QuoteException(NOT_FOUND_QUOTE, BAD_REQUEST.value()));
    }


    private void ifExistItemThrow(long id) {
        if (itemQuoteRepository.existsById(id))
            throw new ItemException(ALREADY_EXIST_ITEM, BAD_REQUEST.value());
    }

    private ItemQuote ifNotExistItemThrow(long id) {
        return itemQuoteRepository.findById(id)
                .orElseThrow(() -> new ItemException(NOT_FOUND_ITEM, BAD_REQUEST.value()));
    }

}
