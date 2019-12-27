package com.exercise.quotes.controller;

import com.exercise.ExerciseApplication;
import com.exercise.quotes.dto.ErrorResponse;
import com.exercise.quotes.dto.QuoteDTO;
import com.exercise.quotes.utils.ApiQuotesConstants;
import com.exercise.quotes.utils.LevelError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.HashSet;

import static com.exercise.quotes.utils.ApiQuotesConstants.ADD_QUOTE;
import static com.exercise.quotes.utils.ErrorMessageQuotesConstant.NAME_CANNOT_BE_EMPTY;
import static com.exercise.quotes.utils.ErrorMessageQuotesConstant.PRICE_CANNOT_BE_NEGATIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    private static final String NAME="name1";
    private static final String NAME_EMPTY="";
    private static final double PRICE_NEGATIVE=-1;
    private static final double PRICE_POSITIVE=1;
    private static final long ID1=1;
    private static final long ID2=2;

    private static final QuoteDTO negativePriceQuoteDTO = new QuoteDTO(ID1,NAME, PRICE_NEGATIVE, new HashSet<>());
    private static final QuoteDTO emptyNameQuoteDTO = new QuoteDTO(ID2,NAME_EMPTY, PRICE_POSITIVE, new HashSet<>());

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }



    @Test
    public void whenPriceNegative_thenReturnsStatus400() throws Exception {
        createTest(negativePriceQuoteDTO, PRICE_CANNOT_BE_NEGATIVE);

    }


    @Test
    public void whenNameIsEmpty_thenReturnsStatus400() throws Exception {
        createTest(emptyNameQuoteDTO, NAME_CANNOT_BE_EMPTY);

    }


    private void createTest(QuoteDTO negativePriceQuoteDTO, String messageerror) throws Exception {
        String body = objectMapper.writeValueAsString(negativePriceQuoteDTO);
        String message = "[" + messageerror + "]";

        MvcResult mvcResult = mvc.perform(post(ADD_QUOTE)
                .contentType("application/json")
                .content(body))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorResponse expectedResponseBody = ErrorResponse.builder()
                .description(message)
                .errorCode(400)
                .level(LevelError.WARNING).build();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(objectMapper.writeValueAsString(expectedResponseBody))
                .isEqualToIgnoringWhitespace(actualResponseBody);
    }
}