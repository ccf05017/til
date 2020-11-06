package com.poppo.toby.services;

import com.poppo.toby.domain.Level;
import com.poppo.toby.domain.User;
import com.poppo.toby.userDao.UserDao;

import java.util.List;

public class UserService {
    public static final int MIN_LOG_COUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();

        for (User user : users) {
            if (canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }

    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();

        if (currentLevel == Level.BASIC) {
            return user.getLogin() >= MIN_LOG_COUNT_FOR_SILVER;
        }
        if (currentLevel == Level.SILVER) {
            return user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD;
        }

        return false;
    }

    private void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }
}
