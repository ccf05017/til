package com.poppo.toby;

import com.poppo.toby.userDao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TobyApplicationTests {
    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
    }

}
