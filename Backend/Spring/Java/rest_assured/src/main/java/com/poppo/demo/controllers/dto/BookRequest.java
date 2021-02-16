package com.poppo.demo.controllers.dto;

public class BookRequest {
    private final String name;
    private final String author;

    public BookRequest(final String name, final String author) {
        this.name = name;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }
}
