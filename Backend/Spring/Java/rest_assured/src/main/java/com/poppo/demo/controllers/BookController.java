package com.poppo.demo.controllers;

import com.poppo.demo.controllers.dto.BookRequest;
import com.poppo.demo.controllers.dto.BookResponse;
import com.poppo.demo.domain.Book;
import com.poppo.demo.domain.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public ResponseEntity getBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookResponse> bookResponses = books.stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(bookResponses);
    }

    @PostMapping("/books")
    public ResponseEntity createBook(@RequestBody BookRequest bookRequest) {
        Book book = new Book(bookRequest.getName(), bookRequest.getAuthor());
        Book saved = bookRepository.save(book);

        return ResponseEntity.created(URI.create("/books/" + saved.getId())).build();
    }
}
