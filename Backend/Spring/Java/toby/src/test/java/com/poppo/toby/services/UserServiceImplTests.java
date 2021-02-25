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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

import static com.poppo.toby.services.UserServiceImpl.MIN_LOG_COUNT_FOR_SILVER;
import static com.poppo.toby.services.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestBeanConfiguration.class)
@DirtiesContext // 스프링 컨텍스트를 수동으로 바꾼 부분이 있음을 명시
class UserServiceImplTests {
    @Autowired
    private UserService userService;

    @Autowired
    private UserService testUserServiceImpl;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserLevelUpgradePolicy userLevelUpgradePolicy;

    private List<User> users;

    @BeforeEach
    void setup() {
        users = Arrays.asList(
                User.builder()
                        .id("11").name("poppo").password("p1").level(Level.BASIC)
                        .login(MIN_LOG_COUNT_FOR_SILVER - 1).recommend(0)
                        .email("test1@gmail.com")
                        .build(),
                User.builder()
                        .id("22").name("saul").password("p2").level(Level.BASIC)
                        .login(MIN_LOG_COUNT_FOR_SILVER).recommend(0)
                        .email("test2@gmail.com")
                        .build(),
                User.builder()
                        .id("33").name("ita").password("p3").level(Level.SILVER)
                        .login(60).recommend(MIN_RECOMMEND_FOR_GOLD - 1)
                        .email("test3@gmail.com")
                        .build(),
                User.builder()
                        .id("44").name("hoo").password("p4").level(Level.SILVER)
                        .login(60).recommend(MIN_RECOMMEND_FOR_GOLD)
                        .email("test4@gmail.com")
                        .build(),
                User.builder()
                        .id("55").name("nugu").password("p5").level(Level.GOLD)
                        .login(100).recommend(Integer.MAX_VALUE)
                        .email("test5@gmail.com")
                        .build()
        );
    }

    @Test
    void bean() {
        assertThat(userService).isNotNull();
    }

    @Test
    void upgradeLevel() {
        MockMailSender mockMailSender = new MockMailSender();
        MockUserDao mockUserDao = new MockUserDao(this.users);
        UserServiceImpl userServiceImpl = new UserServiceImpl(mockUserDao, userLevelUpgradePolicy, mockMailSender);

        userServiceImpl.upgradeLevels();

        List<User> updated = mockUserDao.getUpdated();

        checkUserAndLevel(updated.get(0), "22", Level.SILVER);
        checkUserAndLevel(updated.get(1), "44", Level.GOLD);

        List<String> requests = mockMailSender.getRequests();
        assertThat(requests.size()).isEqualTo(2);
        assertThat(requests.get(0)).isEqualTo(users.get(1).getEmail());
        assertThat(requests.get(1)).isEqualTo(users.get(3).getEmail());
    }

    private void checkUserAndLevel(User user, String expectedId, Level expectedLevel) {
        assertThat(user.getId()).isEqualTo(expectedId);
        assertThat(user.getLevel()).isEqualTo(expectedLevel);
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
    @DirtiesContext
    @Test
    void allUpgradeOrNotTest() {
        // 수동 프록시 방식
//        UserService testUserService = new UserServiceTx(testUserServiceImpl, transactionManager);
        // 다이내믹 프록시 방식
//        TransactionHandler transactionHandler =
//                new TransactionHandler(transactionManager, testUserServiceImpl, "upgradeLevels");
//        UserService testUserService = (UserService) Proxy.newProxyInstance(
//                getClass().getClassLoader(),
//                new Class[] { UserService.class },
//                transactionHandler
//        );
        // 스프링 다이내믹 프록시 방식
//        ProxyFactoryBean proxyFactoryBean = applicationContext.getBean("&userService", ProxyFactoryBean.class);
//        proxyFactoryBean.setTarget(testUserServiceImpl);
//        UserService testUserService = (UserService) proxyFactoryBean.getObject();

        // 테스트용 fixture 처리
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        // when, then
        assertThatThrownBy(testUserServiceImpl::upgradeLevels).isInstanceOf(TestUserServiceException.class);

        checkLevelUpgraded(users.get(1), false);
    }

    @DisplayName("자동 생성된 프록시 타입 확인")
    @Test
    void checkIsProxy() {
        assertThat(testUserServiceImpl).isInstanceOf(Proxy.class);
    }
}
