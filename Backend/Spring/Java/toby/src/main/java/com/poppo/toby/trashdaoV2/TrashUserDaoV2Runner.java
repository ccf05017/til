package com.poppo.toby.trashdaoV2;

import com.poppo.toby.domain.User;

import java.sql.SQLException;

public class TrashUserDaoV2Runner {
    public static void main(String[] args) throws SQLException {
        NUserDao nUserDao = new NUserDao();

        User userN = new User();
        userN.setId("userN");
        userN.setName("poppoN");
        userN.setPassword("password");

        nUserDao.add(userN);

        System.out.println(userN.getId() + " add success");

        User foundUserN = nUserDao.get(userN.getId());
        System.out.println(foundUserN.getName());
        System.out.println(foundUserN.getPassword());

        System.out.println(foundUserN.getId() + " get success");

        DUserDao dUserDao = new DUserDao();

        User userD = new User();
        userD.setId("userD");
        userD.setName("poppoD");
        userD.setPassword("password");

        dUserDao.add(userD);

        System.out.println(userD.getId() + " add success");

        User foundUserD = dUserDao.get(userD.getId());
        System.out.println(foundUserD.getName());
        System.out.println(foundUserD.getPassword());

        System.out.println(foundUserD.getId() + " get success");
    }
}
