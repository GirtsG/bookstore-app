package com.bookstore.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("books")
public record Book(
        @Id Long id,
        String name,
        String author,
        BigDecimal price,
        LocalDateTime createdAt
) {
}
