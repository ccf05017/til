package com.poppo.toby.userDao;

import com.poppo.toby.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class NotBadUserDaoTests {
    @Test
    public void addAndGet() throws SQLException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                NotBadUserDaoFactory.class);

        NotBadUserDao userDao = applicationContext.getBean("notBadUserDao", NotBadUserDao.class);

        userDao.deleteAll();

        assertThat(userDao.getCount()).isEqualTo(0);

        String testId = "userN";
        String testName = "poppoN";
        String password = "passowrd";

        User userN = new User();
        userN.setId(testId);
        userN.setName(testName);
        userN.setPassword(password);

        userDao.add(userN);

        assertThat(userDao.getCount()).isEqualTo(1);

        assertThat(userN.getId()).isEqualTo(testId);
        assertThat(userN.getName()).isEqualTo(testName);
        assertThat(userN.getPassword()).isEqualTo(password);
    }
}
