package com.poppo.toby.countingDao;

import com.poppo.toby.trashDaoV3.ConnectionMaker;
import com.poppo.toby.trashDaoV3.DConnectionMaker;
import com.poppo.toby.trashDaoV4.TrashUserDaoV4;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {
    @Bean
    public TrashUserDaoV4 trashUserDaoV4() {
        return new TrashUserDaoV4(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    public ConnectionMaker realConnectionMaker() {
        return new DConnectionMaker();
    }
}
