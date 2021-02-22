package com.poppo.toby.services;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;

public class TxProxyFactoryBean implements FactoryBean<Object> {
    private final PlatformTransactionManager transactionManager;
    private final Object target;
    private final String pattern;
    private final Class<?> serviceInterface;

    public TxProxyFactoryBean(final PlatformTransactionManager transactionManager, final Object target,
                              final String pattern, final Class<?> serviceInterface) {
        this.transactionManager = transactionManager;
        this.target = target;
        this.pattern = pattern;
        this.serviceInterface = serviceInterface;
    }

    @Override
    public Object getObject() throws Exception {
        TransactionHandler transactionHandler = new TransactionHandler(this.transactionManager, this.target, this.pattern);
        return Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] { this.serviceInterface },
                transactionHandler
        );
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
