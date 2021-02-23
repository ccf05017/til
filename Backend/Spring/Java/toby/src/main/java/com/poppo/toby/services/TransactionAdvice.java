package com.poppo.toby.services;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionAdvice implements MethodInterceptor {
    private final PlatformTransactionManager platformTransactionManager;

    public TransactionAdvice(final PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Object proceeded = invocation.proceed();
            platformTransactionManager.commit(transactionStatus);
            return proceeded;
        } catch(RuntimeException e) {
            platformTransactionManager.rollback(transactionStatus);
            throw e;
        }
    }
}
