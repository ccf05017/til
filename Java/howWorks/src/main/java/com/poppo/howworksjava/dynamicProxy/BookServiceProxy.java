package com.poppo.howworksjava.dynamicProxy;

// 프록시 구현체
public class BookServiceProxy implements BookService {

    BookService bookService;

    public BookServiceProxy(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void rent(Book book) {

        System.out.println("Before BookService");
        bookService.rent(book);
        System.out.println("After BookService");
    }
}
