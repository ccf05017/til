package com.poppo.toby.userDao;

import com.poppo.toby.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.SQLException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NotBadUserDaoTests {
    private NotBadUserDao userDao;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setup() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/data/testdb");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        userDao = new NotBadUserDao(dataSource);

        user1 = User.builder().id("ccf05017").name("poppo").password("password").build();
        user2 = User.builder().id("ccf05018").name("ita").password("password").build();
        user3 = User.builder().id("ccf05019").name("hoojjang").password("password").build();
    }

    @Test
    public void addAndGet() throws SQLException {
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
        assertThatThrownBy(() -> userDao.get("1")).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void getCountTest() throws SQLException {
        String testPassword = "password";

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
