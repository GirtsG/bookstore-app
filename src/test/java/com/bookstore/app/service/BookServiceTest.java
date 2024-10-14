package com.bookstore.app.service;

import com.bookstore.app.model.Book;
import com.bookstore.app.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddBook_Success() {
        Book newBook = new Book(null, "Java Programming", "John Doe", BigDecimal.valueOf(29.99), LocalDateTime.now());
        when(bookRepository.findByName(anyString())).thenReturn(Mono.empty()); // No book with the same name
        when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(newBook));

        Mono<Book> result = bookService.addBook("Java Programming", "John Doe", BigDecimal.valueOf(29.99));

        StepVerifier.create(result)
                .expectNextMatches(book -> book.name().equals("Java Programming"))
                .verifyComplete();

        verify(bookRepository, times(1)).findByName("Java Programming");
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testAddBook_AlreadyExists() {
        Book existingBook = new Book(1L, "Java Programming", "John Doe", BigDecimal.valueOf(29.99), LocalDateTime.now());

        when(bookRepository.findByName(anyString())).thenReturn(Mono.just(existingBook));

        Mono<Book> result = bookService.addBook("Java Programming", "John Doe", BigDecimal.valueOf(29.99));

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && throwable.getMessage().contains("Book with name 'Java Programming' already exists"))
                .verify();

        verify(bookRepository, never()).save(any(Book.class));
    }


    @Test
    public void testGetAllBooks() {
        Book book1 = new Book(1L, "Java Programming", "John Doe", BigDecimal.valueOf(29.99), LocalDateTime.now());
        Book book2 = new Book(2L, "Spring Boot", "Jane Doe", BigDecimal.valueOf(39.99), LocalDateTime.now());

        when(bookRepository.findAllByOrderByCreatedAtDesc(any())).thenReturn(Flux.just(book1, book2));

        Flux<Book> result = bookService.getAllBooks(0, 50);

        StepVerifier.create(result)
                .expectNext(book1)
                .expectNext(book2)
                .verifyComplete();

        verify(bookRepository, times(1)).findAllByOrderByCreatedAtDesc(any());
    }
}
