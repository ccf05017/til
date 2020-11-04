package com.poppo.toby.domain;

import com.poppo.toby.domain.exceptions.NotExistUserLevelException;

import java.util.Arrays;

public enum Level {
    BASIC(1), SILVER(2), GOLD(3);

    private final int value;

    Level(int value) {
        this.value = value;
    }

    public static Level of(int value) {
        return Arrays.stream(Level.values())
                .filter(level -> level.value == value)
                .findFirst()
                .orElseThrow(() -> new NotExistUserLevelException("Not exist such value level: " + value));
    }

    public int toInt() {
        return this.value;
    }
}
