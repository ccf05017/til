package com.poppo.toby;

import com.poppo.toby.services.*;
import com.poppo.toby.userDao.UserDao;
import com.poppo.toby.userDao.UserDaoJdbc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BeanConfiguration {
    @Bean(name = "userService")
    public TxProxyFactoryBean txProxyFactoryBean() {
        return new TxProxyFactoryBean(transactionManager(), userServiceImpl(), "upgradeLevels", UserService.class);
    }

    @Bean
    public Object userService() throws Exception {
        return txProxyFactoryBean().getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public UserLevelUpgradePolicy userLevelUpgradePolicy() {
        return new NormalUserLevelUpgradePolicy();
    }

    @Bean
    public UserDao userDao() {
        return new UserDaoJdbc(dataSource());
    }

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("mail.server.com");
        return mailSender;
    }

    @Bean
    public UserServiceImpl userServiceImpl() {
        return new UserServiceImpl(userDao(), userLevelUpgradePolicy(), mailSender());
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/data/toby");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }
}
