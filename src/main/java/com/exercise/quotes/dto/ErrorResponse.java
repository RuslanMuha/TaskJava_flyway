package com.exercise.quotes.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class ErrorResponse {
    private int errorCode;
    private String description;
    private String level;
}
