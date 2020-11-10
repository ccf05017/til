package com.poppo.toby;

import com.poppo.toby.services.NormalUserLevelUpgradePolicy;
import com.poppo.toby.services.UserLevelUpgradePolicy;
import com.poppo.toby.services.UserService;
import com.poppo.toby.userDao.NotBadUserDao;
import com.poppo.toby.userDao.UserDao;
import com.poppo.toby.userDao.UserDaoJdbc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class BeanConfiguration {
    @Bean
    public NotBadUserDao notBadUserDao() {
        return new NotBadUserDao(dataSource());
    }

    @Bean
    public UserService userService() {
        return new UserService(userDao(), userLevelUpgradePolicy(), dataSource());
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
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/data/toby");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }
}
