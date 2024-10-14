package com.bookstore.app.service;

import com.bookstore.app.model.Book;
import com.bookstore.app.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    public Mono<Book> addBook(String name, String author, BigDecimal price) {
        return bookRepository.findByName(name)
                .flatMap(existingBook -> Mono.<Book>error(new RuntimeException("Book with name '" + name + "' already exists")))
                .switchIfEmpty(Mono.defer(() -> bookRepository.save(new Book(null, name, author, price, LocalDateTime.now()))));
    }

    public Flux<Book> getAllBooks(int page, int size) {
        return bookRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
    }
}
