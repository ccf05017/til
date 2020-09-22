package com.poppo.howworksjava.codecoverage;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MoimTests {

    @Test
    public void isFull() {

        int max = 100;
        int current = 10;
        Moim moim = Moim.createMoim(max, current);

        assertThat(moim.isEnrollmentFull()).isFalse();
    }

}