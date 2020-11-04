package com.poppo.toby.services;

import com.poppo.toby.TestBeanConfiguration;
import com.poppo.toby.domain.Level;
import com.poppo.toby.domain.User;
import com.poppo.toby.userDao.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestBeanConfiguration.class)
class UserServiceTests {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    private List<User> users;

    @BeforeEach
    void setup() {
        users = Arrays.asList(
                User.builder().id("11").name("poppo").password("p1").level(Level.BASIC).login(49).recommend(0).build(),
                User.builder().id("22").name("saul").password("p2").level(Level.BASIC).login(50).recommend(0).build(),
                User.builder().id("33").name("ita").password("p3").level(Level.SILVER).login(60).recommend(29).build(),
                User.builder().id("44").name("hoo").password("p4").level(Level.SILVER).login(60).recommend(30).build(),
                User.builder().id("55").name("nugu").password("p5").level(Level.GOLD).login(100).recommend(100).build()
        );
    }

    @Test
    void bean() {
        assertThat(userService).isNotNull();
    }

    @Test
    void upgradeLevel() {
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);
    }

    private void checkLevel(User user, Level expectedLevel) {
        User updatedUser = userDao.get(user.getId());
        assertThat(updatedUser.getLevel()).isEqualTo(expectedLevel);
    }
}
