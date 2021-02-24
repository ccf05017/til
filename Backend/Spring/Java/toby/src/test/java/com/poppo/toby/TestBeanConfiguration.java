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
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class TestBeanConfiguration {
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
    public UserServiceImpl userService() {
        return new UserServiceImpl(userDao(), userLevelUpgradePolicy(), mailSender());
    }

    @Bean
    public TransactionAdvice transactionAdvice() {
        return new TransactionAdvice(transactionManager());
    }

    @Bean
    public DefaultPointcutAdvisor transactionAdvisor() {
        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
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
