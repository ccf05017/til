package com.poppo.toby.dao;

import com.poppo.toby.domain.User;

import java.sql.SQLException;

public class DaoTestRunner {
    public static void main(String[] args) throws SQLException {
        // 아주아주 안좋은 형태의 Test Runner
        // 실습용으로 구현
        TrashUserDao trashUserDao = new TrashUserDao();

        User user = new User();
        user.setId("ccf05017");
        user.setName("poppo");
        user.setPassword("password");

        trashUserDao.add(user);

        System.out.println(user.getId() + " add success");

        User user2 = trashUserDao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " get success");
    }
}
