package com.poppo.toby.userDao;

import com.poppo.toby.trashDaoV3.ConnectionMaker;
import com.poppo.toby.trashDaoV3.NConnectionMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotBadUserDaoFactory {
    @Bean
    public NotBadUserDao notBadUserDao() {
        return new NotBadUserDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new NConnectionMaker();
    }
}