package com.poppo.howworksjava.dynamicProxy;

import org.junit.jupiter.api.Test;

class DefaultBookServiceTest {

    BookService defaultBookService = new DefaultBookService();
    BookService bookService = new BookServiceProxy(defaultBookService);

    @Test
    public void proxyTest() {

        Book book = new Book("spring");
        bookService.rent(book);
    }
}