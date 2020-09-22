package com.poppo.toby.userDao;

import com.poppo.toby.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    public void getFailTest() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                NotBadUserDaoFactory.class);

        NotBadUserDao userDao = applicationContext.getBean("notBadUserDao", NotBadUserDao.class);

        assertThatThrownBy(() -> userDao.get("1")).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void getCountTest() throws SQLException {
        String testPassword = "password";

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                NotBadUserDaoFactory.class);

        NotBadUserDao userDao = applicationContext.getBean("notBadUserDao", NotBadUserDao.class);

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        IntStream.range(1, 4).forEach(num -> {
            try {
                userDao.add(User.builder()
                        .id("poppo" + num)
                        .name("poppo" + num)
                        .password(testPassword)
                        .build()
                );
                assertThat(userDao.getCount()).isEqualTo(num);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
