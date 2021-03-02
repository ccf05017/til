package com.poppo.toby;

import com.poppo.toby.services.*;
import com.poppo.toby.userDao.UserDao;
import com.poppo.toby.userDao.UserDaoJdbc;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;

@Configuration
public class TestBeanConfiguration {
    @Bean
    public UserService testUserServiceImpl() {
        return new TestUserServiceImpl(userDao(), userLevelUpgradePolicy(), mailSender());
    }

    @Bean
    public NameMatchClassMethodPointcut transactionPointcut() {
        NameMatchClassMethodPointcut nameMatchClassMethodPointcut = new NameMatchClassMethodPointcut();
        nameMatchClassMethodPointcut.setMappedClassName("*ServiceImpl");
        nameMatchClassMethodPointcut.setMappedName("upgrade*");

        return nameMatchClassMethodPointcut;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userDao(), userLevelUpgradePolicy(), mailSender());
    }

    @Bean
    public TransactionInterceptor transactionAdvice() {
        NameMatchTransactionAttributeSource transactionAttributeSource = new NameMatchTransactionAttributeSource();

        DefaultTransactionAttribute getTransactionAttribute = new DefaultTransactionAttribute();
        getTransactionAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        getTransactionAttribute.setReadOnly(true);
        getTransactionAttribute.setTimeout(30);

        DefaultTransactionAttribute upgradeTransactionAttribute = new DefaultTransactionAttribute();
        upgradeTransactionAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        upgradeTransactionAttribute.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);

        DefaultTransactionAttribute defaultTransactionAttribute = new DefaultTransactionAttribute();
        getTransactionAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        transactionAttributeSource.addTransactionalMethod("get*", getTransactionAttribute);
        transactionAttributeSource.addTransactionalMethod("upgrade*", upgradeTransactionAttribute);
        transactionAttributeSource.addTransactionalMethod("*", defaultTransactionAttribute);

        return new TransactionInterceptor(transactionManager(), transactionAttributeSource);
    }

    @Bean
    public DefaultPointcutAdvisor transactionAdvisor() {
        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
    }

    @Bean
    public TransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public UserDao userDao() {
        return new UserDaoJdbc(dataSource());
    }

    @Bean
    public UserLevelUpgradePolicy userLevelUpgradePolicy() {
        return new NormalUserLevelUpgradePolicy();
    }

    @Bean
    public MailSender mailSender() {
        return new DummyMailService();
    }

    @Bean
    public UserServiceImpl userServiceImpl() {
        return new UserServiceImpl(userDao(), userLevelUpgradePolicy(), mailSender());
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/data/testdb");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }
}
