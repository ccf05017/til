package com.poppo.toby.datasourceExample;

import com.poppo.toby.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class DataSourceTest {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);

        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);

        User userN = new User();
        userN.setId("userN6");
        userN.setName("poppoN5");
        userN.setPassword("password");

        userDao.add(userN);
        User user = userDao.get(userN.getId());

        System.out.println("Hello, " + user.getId());
    }
}
