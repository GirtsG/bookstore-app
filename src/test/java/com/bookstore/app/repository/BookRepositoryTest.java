package com.bookstore.app.repository;

import com.bookstore.app.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@DataR2dbcTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private DatabaseClient databaseClient;

    private Book testBook;

    @BeforeEach
    public void setUp() {
        testBook = new Book(null, "Java Programming", "John Doe", BigDecimal.valueOf(29.99), LocalDateTime.now());
        databaseClient.sql("DELETE FROM books").then().block(); // Clean up database
        bookRepository.save(testBook).block(); // Insert a test book
    }

    @Test
    public void testFindByName() {
        Mono<Book> result = bookRepository.findByName("Java Programming");

        StepVerifier.create(result)
                .expectNextMatches(book -> book.name().equals("Java Programming"))
                .verifyComplete();
    }

    @Test
    public void testSaveBook() {
        Book newBook = new Book(null, "Spring Boot", "Jane Doe", BigDecimal.valueOf(39.99), LocalDateTime.now());
        Mono<Book> result = bookRepository.save(newBook);

        StepVerifier.create(result)
                .expectNextMatches(book -> book.name().equals("Spring Boot"))
                .verifyComplete();
    }
}
