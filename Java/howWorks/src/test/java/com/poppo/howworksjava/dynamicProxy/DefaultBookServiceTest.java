package com.poppo.howworksjava.dynamicProxy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static net.bytebuddy.matcher.ElementMatchers.named;

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
    public void interfaceDynamicProxyTest() {

        Book book = new Book("spring");
        bookServiceDynamicProxy.rent(book);
        bookServiceDynamicProxy.returnBook(book);
    }

    @Test
    public void classDynamicProxyByCglib() {

        MethodInterceptor handler = new MethodInterceptor() {

            BookService bookService = new DefaultBookService();

            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

                // 특정 메소드에서만 실행하고 싶을 때 조건
                if (method.getName().equals("rent")) {
                    System.out.println("before proxy");
                    Object invoke = method.invoke(bookService, objects);
                    System.out.println("after proxy");

                    return invoke;
                }

                return method.invoke(bookService, objects);
            }
        };

        // Enhancer는 cglib에서 가져오는 것
        BookService bookService = (BookService) Enhancer.create(BookService.class, handler);

        Book book = new Book("spring");
        bookService.rent(book);
        bookService.returnBook(book);
    }

    @Test
    public void classDynamicProxyByByteBuddy() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class<? extends BookService> proxyClass = new ByteBuddy()
                .subclass(DefaultBookService.class)
                .method(named("rent")).intercept(InvocationHandlerAdapter.of(new InvocationHandler() {
                    BookService bookService = new DefaultBookService();
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        System.out.println("before proxy");
                        Object invoke = method.invoke(bookService, args);
                        System.out.println("after proxy");

                        return invoke;
                    }
                }))
                .make()
                .load(DefaultBookService.class.getClassLoader()).getLoaded();
        BookService bookService = proxyClass.getConstructor(null).newInstance();

        Book book = new Book("spring");
        bookService.rent(book);
        bookService.returnBook(book);
    }
}