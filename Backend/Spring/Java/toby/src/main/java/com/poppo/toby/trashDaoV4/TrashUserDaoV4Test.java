package com.poppo.toby.trashDaoV4;

import com.poppo.toby.domain.User;
import com.poppo.toby.trashDaoV3.DConnectionMaker;
import com.poppo.toby.trashDaoV3.NConnectionMaker;

import java.sql.SQLException;

public class TrashUserDaoV4Test {
    public static void main(String[] args) throws SQLException {
        NConnectionMaker nConnectionMaker = new NConnectionMaker();
        DConnectionMaker dConnectionMaker = new DConnectionMaker();

        TrashUserDaoV4 nUserDao = new TrashUserDaoV4(nConnectionMaker);
        TrashUserDaoV4 dUserDao = new TrashUserDaoV4(dConnectionMaker);

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
