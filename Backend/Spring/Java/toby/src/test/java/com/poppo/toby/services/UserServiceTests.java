package com.poppo.toby.services;

import com.poppo.toby.TestBeanConfiguration;
import com.poppo.toby.domain.Level;
import com.poppo.toby.domain.User;
import com.poppo.toby.userDao.UserDao;
import com.poppo.toby.userDao.exceptions.TestUserServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static com.poppo.toby.services.UserService.MIN_LOG_COUNT_FOR_SILVER;
import static com.poppo.toby.services.UserService.MIN_RECOMMEND_FOR_GOLD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestBeanConfiguration.class)
class UserServiceTests {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserLevelUpgradePolicy userLevelUpgradePolicy;

    @Autowired
    private DataSource dataSource;

    private List<User> users;

    @BeforeEach
    void setup() {
        users = Arrays.asList(
                User.builder()
                        .id("11").name("poppo").password("p1").level(Level.BASIC)
                        .login(MIN_LOG_COUNT_FOR_SILVER - 1).recommend(0)
                        .build(),
                User.builder()
                        .id("22").name("saul").password("p2").level(Level.BASIC)
                        .login(MIN_LOG_COUNT_FOR_SILVER).recommend(0)
                        .build(),
                User.builder()
                        .id("33").name("ita").password("p3").level(Level.SILVER)
                        .login(60).recommend(MIN_RECOMMEND_FOR_GOLD - 1)
                        .build(),
                User.builder()
                        .id("44").name("hoo").password("p4").level(Level.SILVER)
                        .login(60).recommend(MIN_RECOMMEND_FOR_GOLD)
                        .build(),
                User.builder()
                        .id("55").name("nugu").password("p5").level(Level.GOLD)
                        .login(100).recommend(Integer.MAX_VALUE)
                        .build()
        );
    }

    @Test
    void bean() {
        assertThat(userService).isNotNull();
    }

    @Test
    void upgradeLevel() throws SQLException {
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User updatedUser = userDao.get(user.getId());
        if (upgraded) {
            assertThat(updatedUser.getLevel()).isEqualTo(user.getLevel().nextLevel());
        } else {
            assertThat(updatedUser.getLevel()).isEqualTo(user.getLevel());
        }
    }

    @DisplayName("신규 유저 등록시 BASIC 레벨로 생성")
    @Test
    void createNewLevelTest() {
        // 좋은 테스트는 아니다.
        // 유닛 테스트 치고 너무 복잡하다.
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevel.getLevel());

        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());
        assertThat(userWithoutLevelRead.getLevel()).isEqualTo(Level.BASIC);
    }

    @DisplayName("트랜잭션 원자성 테스트")
    @Test
    void allUpgradeOrNotTest() {
        UserService userService =
                new UserService.TestUserService(userDao, userLevelUpgradePolicy, dataSource, users.get(3).getId());

        userDao.deleteAll();

        for (User user : users) {
            userDao.add(user);
        }

        assertThatThrownBy(userService::upgradeLevels).isInstanceOf(TestUserServiceException.class);

        checkLevelUpgraded(users.get(1), false);
    }
}
