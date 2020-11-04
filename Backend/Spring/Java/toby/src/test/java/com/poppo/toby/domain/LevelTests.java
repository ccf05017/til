package com.poppo.toby.domain;

import com.poppo.toby.domain.exceptions.NotExistUserLevelException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LevelTests {
    @DisplayName("값을 레벨로 전환할 수 있다.")
    @ParameterizedTest
    @MethodSource("valueToLevelResources")
    void valueToLevelTest(int value, Level expectedLevel) {
        assertThat(Level.of(value)).isEqualTo(expectedLevel);
    }
    public static Stream<Arguments> valueToLevelResources() {
        return Stream.of(
                Arguments.of(1, Level.BASIC),
                Arguments.of(2, Level.SILVER),
                Arguments.of(3, Level.GOLD)
        );
    }

    @DisplayName("범위를 벗어난 값으로 레벨을 생성할 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 4, 5})
    void valueToLevelFailTest(int value) {
        assertThatThrownBy(() -> Level.of(value)).isInstanceOf(NotExistUserLevelException.class);
    }
}
