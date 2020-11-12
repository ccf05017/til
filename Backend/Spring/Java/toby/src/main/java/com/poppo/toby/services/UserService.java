package com.poppo.toby.services;

import com.poppo.toby.domain.Level;
import com.poppo.toby.domain.User;
import com.poppo.toby.userDao.UserDao;
import com.poppo.toby.userDao.exceptions.TestUserServiceException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

public class UserService {
    public static final int MIN_LOG_COUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    private UserDao userDao;
    private UserLevelUpgradePolicy userLevelUpgradePolicy;
    private PlatformTransactionManager transactionManager;
    private MailSender mailSender;

    public UserService(UserDao userDao, UserLevelUpgradePolicy userLevelUpgradePolicy,
                       PlatformTransactionManager transactionManager, MailSender mailSender) {
        this.userDao = userDao;
        this.userLevelUpgradePolicy = userLevelUpgradePolicy;
        this.transactionManager = transactionManager;
        this.mailSender = mailSender;
    }

    public void upgradeLevels() {
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            List<User> users = userDao.getAll();

            // User 객체가 policy를 주입받고 스스로 레벨업 하는 방법도 좋을듯 (if를 걷어낼 수 있음)
            for (User user : users) {
                if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }

            transactionManager.commit(transaction);
        } catch (Exception e) {
            transactionManager.rollback(transaction);
            throw e;
        }
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }

    protected void upgradeLevel(User user) {
        userLevelUpgradePolicy.upgradeLevel(user);
        userDao.update(user);
        sendUpgradeEmail(user);
    }

    // 스프링이 제공하는 메일 추상화
    private void sendUpgradeEmail(User user) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setFrom("tobyPractise@gmail.com");
        simpleMailMessage.setSubject("Upgrade 안내");
        simpleMailMessage.setText(user.getName() + "님의 등급이 " + user.getLevel().name() + " 으로 업그레이드 됐습니다.");

        mailSender.send(simpleMailMessage);
    }

    // 가장 전형적인 자바 메일 발송 코드
//    private void sendUpgradeEmail(User user) {
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "mail.ksug.org");
//        Session session = Session.getInstance(props);
//
//        MimeMessage mimeMessage = new MimeMessage(session);
//        try {
//            mimeMessage.setFrom(new InternetAddress("admin@ksug.org"));
//            mimeMessage.addRecipient(Message.RecipientType.TO,
//                    new InternetAddress(user.getEmail()));
//            mimeMessage.setSubject("Upgrade 안내");
//            mimeMessage.setText(user.getName() + "님의 등급이 " + user.getLevel().name() + " 으로 업그레이드 됐습니다.");
//            Transport.send(mimeMessage);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }

    static class TestUserService extends UserService {
        private String id;

        public TestUserService(UserDao userDao, UserLevelUpgradePolicy userLevelUpgradePolicy,
                               PlatformTransactionManager transactionManager, MailSender mailSender, String id) {
            super(userDao, userLevelUpgradePolicy, transactionManager, mailSender);
            this.id = id;
        }

        @Override
        protected void upgradeLevel(User user) {
            if (user.getId().equals(id)) {
                throw new TestUserServiceException();
            }
            super.upgradeLevel(user);
        }
    }
}
