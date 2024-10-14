package com.bookstore.app.controller;

import com.bookstore.app.model.Book;
import com.bookstore.app.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddBook_Success() {
        Book newBook = new Book(null, "Java Programming", "John Doe", BigDecimal.valueOf(29.99), LocalDateTime.now());
        when(bookService.addBook(any(String.class), any(String.class), any(BigDecimal.class)))
                .thenReturn(Mono.just(newBook));

        Mono<ResponseEntity<String>> response = bookController.addBook("Java Programming", "John Doe", BigDecimal.valueOf(29.99));

        StepVerifier.create(response)
                .expectNextMatches(result -> result.getBody().equals("Book added successfully"))
                .verifyComplete();

        verify(bookService, times(1)).addBook("Java Programming", "John Doe", BigDecimal.valueOf(29.99));
    }

    @Test
    public void testAddBook_AlreadyExists() {
        when(bookService.addBook(any(String.class), any(String.class), any(BigDecimal.class)))
                .thenReturn(Mono.error(new RuntimeException("Book with name 'Java Programming' already exists")));

        Mono<ResponseEntity<String>> response = bookController.addBook("Java Programming", "John Doe", BigDecimal.valueOf(29.99));

        StepVerifier.create(response)
                .expectNextMatches(result -> result.getBody().equals("Book with name 'Java Programming' already exists"))
                .verifyComplete();

        verify(bookService, times(1)).addBook("Java Programming", "John Doe", BigDecimal.valueOf(29.99));
    }

    @Test
    public void testGetBooks() {
        Book book1 = new Book(1L, "Java Programming", "John Doe", BigDecimal.valueOf(29.99), LocalDateTime.now());
        Book book2 = new Book(2L, "Spring Boot", "Jane Doe", BigDecimal.valueOf(39.99), LocalDateTime.now());
        when(bookService.getAllBooks(anyInt(), anyInt()))
                .thenReturn(Flux.just(book1, book2));

        Flux<Book> result = bookController.getBooks(0, 50);

        StepVerifier.create(result)
                .expectNext(book1)
                .expectNext(book2)
                .verifyComplete();

        verify(bookService, times(1)).getAllBooks(0, 50);
    }
}
