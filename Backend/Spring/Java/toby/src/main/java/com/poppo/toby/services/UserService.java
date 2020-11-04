package com.poppo.toby.services;

import com.poppo.toby.userDao.UserDao;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
}
