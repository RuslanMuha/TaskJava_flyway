package com.exercise.quotes.service;

import com.exercise.quotes.dto.ItemDTO;

import java.util.Set;

public interface ItemService {

    ItemDTO createItem(long idQuote, ItemDTO itemdto);
    ItemDTO updateItem(ItemDTO itemDto);
    void deleteItem(long id);
    Set<ItemDTO> getAllItems();
    ItemDTO getItems(long id);
}
