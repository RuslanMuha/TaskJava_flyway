package com.exercise.quotes.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Getter
@Setter
public class ItemDTO {
    private long id;
    private String name;
}
