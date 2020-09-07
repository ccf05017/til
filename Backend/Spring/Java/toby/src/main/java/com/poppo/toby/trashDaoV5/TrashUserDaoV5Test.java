package com.poppo.toby.trashDaoV5;

import com.poppo.toby.domain.User;
import com.poppo.toby.trashDaoV4.TrashUserDaoV4;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class TrashUserDaoV5Test {
    public static void main(String[] args) throws SQLException {
        // DaoFactory가 만들어준 UserDao를 사용하기만 한다
        // 테스트코드는 UserDao가 어떻게 만들어졌는지는 알지도 못하고 알고 싶지도 않다.
//        TrashUserDaoV4 userDao = new DaoFactory().trashUserDaoV4();
//
//        User userN = new User();
//        userN.setId("userN2");
//        userN.setName("poppoN2");
//        userN.setPassword("password");
//
//        userDao.add(userN);
//
//        System.out.println(userN.getId() + " add success");
//
//        User foundUserN = userDao.get(userN.getId());
//        System.out.println(foundUserN.getName());
//        System.out.println(foundUserN.getPassword());
//
//        System.out.println(foundUserN.getId() + " get success");

        // Application Context를 활용해보자
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);

        TrashUserDaoV4 userDao = applicationContext.getBean("trashUserDaoV4", TrashUserDaoV4.class);

        User userN = new User();
        userN.setId("userN3");
        userN.setName("poppoN3");
        userN.setPassword("password");

        userDao.add(userN);

        System.out.println(userN.getId() + " add success");

        User foundUserN = userDao.get(userN.getId());
        System.out.println(foundUserN.getName());
        System.out.println(foundUserN.getPassword());

        System.out.println(foundUserN.getId() + " get success");
    }
}
