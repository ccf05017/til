package com.poppo.toby.services;

import com.poppo.toby.TestBeanConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestBeanConfiguration.class)
class UserServiceTests {
    @Autowired
    private UserService userService;

    @Test
    public void bean() {
        assertThat(userService).isNotNull();
    }
}
