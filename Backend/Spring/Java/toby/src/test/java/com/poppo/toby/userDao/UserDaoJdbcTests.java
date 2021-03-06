package com.poppo.toby.userDao;

import com.poppo.toby.domain.Level;
import com.poppo.toby.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

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

        user1 = User.builder()
                .id("ccf05017").name("poppo").password("password").level(Level.BASIC).login(1).recommend(0)
                .email("test1@gmail.com")
                .build();
        user2 = User.builder()
                .id("ccf05018").name("ita").password("password").level(Level.SILVER).login(55).recommend(10)
                .email("test2@gmail.com")
                .build();
        user3 = User.builder()
                .id("ccf05019").name("hoojjang").password("password").level(Level.GOLD).login(100).recommend(40)
                .email("test3@gmail.com")
                .build();
    }

    @Test
    void addAndGet() {
        userDao.deleteAll();

        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);

        assertThat(userDao.getCount()).isEqualTo(1);

        assertThat(user1.getId()).isEqualTo("ccf05017");
        assertThat(user1.getName()).isEqualTo("poppo");
        assertThat(user1.getPassword()).isEqualTo("password");
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
                        .level(Level.BASIC)
                        .login(num)
                        .recommend(num)
                        .email("test" + num + "@gmail.com")
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
        assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
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

    @Test
    void update() {
        userDao.deleteAll();

        userDao.add(user1);
        userDao.add(user2);

        user1.setName("newUser");
        user1.setPassword("newPassword");
        user1.setLevel(Level.GOLD);
        user1.setLogin(4);
        user1.setRecommend(4);
        user1.setEmail("newMail@gamil.com");

        userDao.update(user1);

        User updatedUser = userDao.get(user1.getId());
        checkSameUser(user1, updatedUser);
        User user2same = userDao.get(user2.getId());
        checkSameUser(user2, user2same);
    }
}
