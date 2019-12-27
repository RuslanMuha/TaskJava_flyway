package com.exercise.quotes.utils;

public interface ApiQuotesConstants {
    String ADD_QUOTE = "/quotes";
    String DELETE_QUOTE = "/quotes/{id}";
    String GET_QUOTE = "/quotes/{name}";
    String GET_ALL_QUOTES = "/quotes";
    String UPDATE_QUOTE = "/quotes/update/{id}";
    String ADD_ITEM = "/item/{idQuote}";
    String UPDATE_ITEM = "/item/update";
    String DELETE_ITEM = "/item/{id}";
    String GET_ALL_ITEMS = "/items";
    String GET_ITEM = "/item/{id}";
}
