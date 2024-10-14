package com.bookstore.app.controller;

import com.bookstore.app.model.Book;
import com.bookstore.app.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public Mono<ResponseEntity<String>> addBook(
            @RequestParam String name,
            @RequestParam String author,
            @RequestParam BigDecimal price) {
        return bookService.addBook(name, author, price)
                .map(book -> ResponseEntity.ok("Book added successfully"))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(e.getMessage())));
    }


    @GetMapping
    public Flux<Book> getBooks(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "50") int size) {
        return bookService.getAllBooks(page, size);
    }
}
