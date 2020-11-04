package com.poppo.toby.services;

import com.poppo.toby.domain.Level;
import com.poppo.toby.domain.User;
import com.poppo.toby.userDao.UserDao;

import java.util.List;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();

        for (User user : users) {
            Boolean isChanged = null;
            if (user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
                user.setLevel(Level.SILVER);
                isChanged = true;
            } else if (user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
                user.setLevel(Level.GOLD);
                isChanged = true;
            } else if (user.getLevel() == Level.GOLD) {
                isChanged = false;
            } else {
                isChanged = false;
            }

            if (isChanged) {
                userDao.update(user);
            }
        }
    }
}
