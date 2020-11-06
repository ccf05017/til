package com.poppo.toby.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTests {
    private User user;

    @BeforeEach
    void setup() {
        user = new User();
    }

    @Test
    void upgradeLevelSuccessTest() {
        Level[] levels = Level.values();

        for (Level level : levels) {
            if (level.nextLevel() == null) {
                continue;
            }
            user.setLevel(level);
            user.upgradeLevel();
            assertThat(user.getLevel()).isEqualTo(level.nextLevel());
        }
    }

    @Test
    void upgradeLevelFailTest() {
        Level[] levels = Level.values();
        for (Level level : levels) {
            if (level.nextLevel() != null) {
                continue;
            }
            user.setLevel(level);
            assertThatThrownBy(() -> user.upgradeLevel()).isInstanceOf(IllegalStateException.class);
        }
    }
}
