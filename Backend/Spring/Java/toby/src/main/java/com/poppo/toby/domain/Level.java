package com.poppo.toby.domain;

import com.poppo.toby.domain.exceptions.NotExistUserLevelException;

import java.util.Arrays;

public enum Level {
    GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER);

    private final int value;
    private final Level next;

    Level(int value, Level next) {
        this.value = value;
        this.next = next;
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

    public Level nextLevel() {
        return this.next;
    }
}
