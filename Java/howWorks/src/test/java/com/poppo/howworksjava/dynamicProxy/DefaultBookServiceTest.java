package com.poppo.howworksjava.dynamicProxy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class DefaultBookServiceTest {

    BookService defaultBookService = new DefaultBookService();
    BookService bookServiceProxy = new BookServiceProxy(defaultBookService);

    BookService bookServiceDynamicProxy = (BookService) Proxy.newProxyInstance(
            BookService.class.getClassLoader(),
            new Class[]{BookService.class},
            new InvocationHandler() {

                // 이 코드가 별로 유연하진 않다....
                // 클래스 기반 프록시 생성이 불가능하다. (인터페이스만 가능)
                // 그래서 이걸 쓰기 편하게 고친 게 Spring AOP Interface
                BookService bookService = new DefaultBookService();
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("Before BookService - dynamic proxy");
                    Object invoke = method.invoke(bookService, args);
                    System.out.println("After BookService - dynamic proxy");
                    return invoke;
                }
            }
    );

    @Test
    public void proxyTest() {

        Book book = new Book("spring");
        bookServiceProxy.rent(book);
    }

    @Test
    public void dynamicProxyTest() {

        Book book = new Book("spring");
        bookServiceDynamicProxy.rent(book);
        bookServiceDynamicProxy.returnBook(book);
    }
}