package com.poppo.howworksjava.dynamicProxy;

public class DefaultBookService implements BookService {

    @Override
    public void rent(Book book) {

        System.out.println(book.getName());
    }

    @Override
    public void returnBook(Book book) {

        System.out.println(book.getName());
    }
}
