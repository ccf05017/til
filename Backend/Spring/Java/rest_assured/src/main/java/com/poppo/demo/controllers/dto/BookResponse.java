package com.poppo.demo.controllers.dto;

import com.poppo.demo.domain.Book;

public class BookResponse {
    private final Long id;
    private final String name;
    private final String author;

    BookResponse(final Long id, final String name, final String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public BookResponse(final Book book) {
        this(book.getId(), book.getName(), book.getAuthor());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }
}
