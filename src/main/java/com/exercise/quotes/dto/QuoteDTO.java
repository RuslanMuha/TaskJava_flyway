package com.exercise.quotes.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

import static com.exercise.quotes.utils.ErrorMessageQuotesConstant.NAME_CANNOT_BE_EMPTY;
import static com.exercise.quotes.utils.ErrorMessageQuotesConstant.NAME_CANNOT_BE_NULL;
import static com.exercise.quotes.utils.ErrorMessageQuotesConstant.PRICE_CANNOT_BE_NEGATIVE;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class QuoteDTO {
    long id;
    @NotEmpty(message = NAME_CANNOT_BE_EMPTY)
    @NotNull(message = NAME_CANNOT_BE_NULL)
    private String name;
    @Positive(message = PRICE_CANNOT_BE_NEGATIVE)
    private double price;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<ItemDTO> items;
}
