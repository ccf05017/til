package com.poppo.toby;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

// JUnit은 정말 메서드를 실행할 때마다 새로운 인스턴스를 만드는가?
// Spring Context는 늘 같은 컨텍스트를 불러오는가?
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestSpringContext.class })
public class LearningTestJUnit {
    @Autowired
    private TestSpringContext testSpringContext;

    static HashSet<LearningTestJUnit> junitTestObjects = new HashSet<>();
    static TestSpringContext contextObject = null;

    @Test
    public void firstRun() {
        assertThat(junitTestObjects).doesNotContain(this);
        junitTestObjects.add(this);

        assertThat(contextObject == null || contextObject == this.testSpringContext).isTrue();
        contextObject = this.testSpringContext;
    }

    @Test
    public void secondRun() {
        assertThat(junitTestObjects).doesNotContain(this);
        junitTestObjects.add(this);

        assertThat(contextObject == null || contextObject == this.testSpringContext).isTrue();
        contextObject = this.testSpringContext;
    }

    @Test
    public void thirdRun() {
        assertThat(junitTestObjects).doesNotContain(this);
        junitTestObjects.add(this);

        assertThat(contextObject == null || contextObject == this.testSpringContext).isTrue();
        contextObject = this.testSpringContext;
    }
}
