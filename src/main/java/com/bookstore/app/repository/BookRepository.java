package com.bookstore.app.repository;

import com.bookstore.app.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, Long> {

    Mono<Book> findByName(String name);

    Flux<Book> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
