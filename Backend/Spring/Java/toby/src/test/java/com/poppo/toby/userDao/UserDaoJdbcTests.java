package com.poppo.toby.userDao;

import com.poppo.toby.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserDaoJdbcTests {
    private UserDao userDao;
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

        userDao = new UserDaoJdbc(dataSource);

        user1 = User.builder().id("ccf05017").name("poppo").password("password").build();
        user2 = User.builder().id("ccf05018").name("ita").password("password").build();
        user3 = User.builder().id("ccf05019").name("hoojjang").password("password").build();
    }

    @Test
    void addAndGet() throws SQLException {
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
    void getFailTest() {
        assertThatThrownBy(() -> userDao.get("1")).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void getCountTest() {
        String testPassword = "password";

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        IntStream.range(1, 4).forEach(num -> {
                userDao.add(User.builder()
                        .id("poppo" + num)
                        .name("poppo" + num)
                        .password(testPassword)
                        .build()
                );
                assertThat(userDao.getCount()).isEqualTo(num);
        });
    }

    @Test
    void getAllTest() {
        // 구멍난 테스트 케이스를 만드는 것보단 낫지만 아래와 같은 반복 테스트가 좋은 건 절대 아니다.
        // 실무에선 좀 더 효율적인 테스트 구현 방안이 없는지 고민하자.
        userDao.deleteAll();

        userDao.add(user1);
        List<User> users = userDao.getAll();
        assertThat(users.size()).isEqualTo(1);
        checkSameUser(user1, users.get(0));

        userDao.add(user2);
        List<User> users2 = userDao.getAll();
        assertThat(users2.size()).isEqualTo(2);
        checkSameUser(user2, users2.get(1));

        userDao.add(user3);
        List<User> users3 = userDao.getAll();
        assertThat(users3.size()).isEqualTo(3);
        checkSameUser(user3, users3.get(2));

        checkSameUser(user1, users3.get(0));
        checkSameUser(user2, users3.get(1));
        checkSameUser(user3, users3.get(2));
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    void getAllWithEmptyListTest() {
        userDao.deleteAll();

        List<User> users = userDao.getAll();

        assertThat(users.size()).isEqualTo(0);
    }

    @Test
    void duplicateIdErrorTest() {
        userDao.deleteAll();

        userDao.add(user1);
        assertThatThrownBy(() -> userDao.add(user1)).isInstanceOf(DuplicateKeyException.class);
    }
}
