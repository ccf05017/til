package com.poppo.toby.trashDaoV4;

import com.poppo.toby.domain.User;
import com.poppo.toby.trashDaoV5.DaoFactory;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class TrashUserDaoV4Tests {
    @Test
    public void addAndGet() throws SQLException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);

        TrashUserDaoV4 userDao = applicationContext.getBean("trashUserDaoV4", TrashUserDaoV4.class);

        String testId = "userN10";
        String testName = "poppoN10";
        String password = "passowrd";

        User userN = new User();
        userN.setId(testId);
        userN.setName(testName);
        userN.setPassword(password);

        userDao.add(userN);

        assertThat(userN.getId()).isEqualTo(testId);
        assertThat(userN.getName()).isEqualTo(testName);
        assertThat(userN.getPassword()).isEqualTo(password);
    }
}
