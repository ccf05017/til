package com.poppo.toby.services;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TransactionHandler implements InvocationHandler {
    private final PlatformTransactionManager transactionManager;
    private final Object target;
    private final String pattern;

    public TransactionHandler(final PlatformTransactionManager transactionManager, final Object target, final String pattern) {
        this.transactionManager = transactionManager;
        this.target = target;
        this.pattern = pattern;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        // 정해진 패턴에 대해서만 트랜잭션 부가 기능을 적용한다.
        if (method.getName().startsWith(pattern)) {
            return invokeInTransaction(method, args);
        }
        return method.invoke(target, args);
    }

    private Object invokeInTransaction(final Method method, final Object[] args) throws Throwable {
        // 실제 트랜잭션 부가 기능 코드
        TransactionStatus transactionStatus = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            Object invokedResult = method.invoke(target, args);
            this.transactionManager.commit(transactionStatus);
            return invokedResult;
        } catch (InvocationTargetException e) {
            this.transactionManager.rollback(transactionStatus);
            throw e.getTargetException();
        }
    }
}
