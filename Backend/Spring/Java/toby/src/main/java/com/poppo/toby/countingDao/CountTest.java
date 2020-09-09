package com.poppo.toby.countingDao;

import com.poppo.toby.domain.User;
import com.poppo.toby.trashDaoV4.TrashUserDaoV4;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class CountTest {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(CountingDaoFactory.class);

        TrashUserDaoV4 userDao = applicationContext.getBean("trashUserDaoV4", TrashUserDaoV4.class);

        User userN = new User();
        userN.setId("userN4");
        userN.setName("poppoN4");
        userN.setPassword("password");

        userDao.add(userN);
        userDao.get(userN.getId());

        CountingConnectionMaker countingConnectionMaker =
                applicationContext.getBean("connectionMaker", CountingConnectionMaker.class);

        System.out.println(countingConnectionMaker.getCount());
    }
}
