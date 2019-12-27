package com.exercise.log.entity;

import com.exercise.log.Operation;
import com.exercise.log.OperationConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "quote_log")
@Entity
public class QuoteLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "created_date")
    LocalDateTime createdDate;
    @Column(name = "quote_id")
    long quoteId;
    @Column(name = "error_code")
    private int errorCode;

    @Column(name = "message")
    private String message;
    @Column(name = "operation", columnDefinition = "INTEGER")
    @Convert(converter = OperationConverter.class)
    Operation operation;
}
