package com.poppo.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String author;

    protected Book() {
    }

    public Book(final Long id, final String name, final String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public Book(String name, String author) {
        this(null, name, author);
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }
}
