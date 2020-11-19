package com.poppo.toby.services;

import com.poppo.toby.domain.Level;
import com.poppo.toby.domain.User;

import static com.poppo.toby.services.UserServiceImpl.MIN_LOG_COUNT_FOR_SILVER;
import static com.poppo.toby.services.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;

public class NormalUserLevelUpgradePolicy implements UserLevelUpgradePolicy {

    @Override
    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();

        if (currentLevel == Level.BASIC) {
            return user.getLogin() >= MIN_LOG_COUNT_FOR_SILVER;
        }
        if (currentLevel == Level.SILVER) {
            return user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD;
        }

        return false;
    }

    @Override
    public void upgradeLevel(User user) {
        user.upgradeLevel();
    }
}
