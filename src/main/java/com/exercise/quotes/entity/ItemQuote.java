package com.exercise.quotes.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "items")
@SQLDelete(sql = "update items set removed=true where id=?")
@Where(clause ="removed=false")
public class ItemQuote  {


    @Id
    private long id;
    private String name;
    @Column(name = "removed", columnDefinition = "boolean default false")
    private boolean removed;

}
