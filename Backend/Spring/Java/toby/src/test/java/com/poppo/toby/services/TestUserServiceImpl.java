package com.poppo.toby.services;

import com.poppo.toby.domain.User;
import com.poppo.toby.userDao.UserDao;
import com.poppo.toby.userDao.exceptions.TestUserServiceException;
import org.springframework.mail.MailSender;

public class TestUserServiceImpl extends UserServiceImpl {
    // Bean 등록 방식으로 테스트할 예정이기 때문에 넣을 방법이 없어서 취한 조치
    private final String id = "44";

    public TestUserServiceImpl(UserDao userDao, UserLevelUpgradePolicy userLevelUpgradePolicy, MailSender mailSender) {
        super(userDao, userLevelUpgradePolicy, mailSender);
    }

    @Override
    protected void upgradeLevel(User user) {
        if (user.getId().equals(id)) {
            throw new TestUserServiceException();
        }
        super.upgradeLevel(user);
    }
}
