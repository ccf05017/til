package com.poppo.toby.simpleSamples.pointcutExpression;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import static org.assertj.core.api.Assertions.assertThat;

public class PointCutTest {
    // 기타 더 자세한 포인트컷 표현식에 대해서는 토비의 스프링 책을 참고하자
    @DisplayName("포인트컷 표현식을 통한 포인트컷 구현")
    @Test
    void pointCutExpressionTest() throws NoSuchMethodException {
        String expression = "execution(public int " +
                "com.poppo.toby.simpleSamples.pointcutExpression.PointCutTest1.minus(int, int))";

        pointcutMatches(expression, true, PointCutTest1.class, "minus", int.class, int.class);
    }

    private void pointcutMatches(String expression, Boolean expected, Class<?> clazz,
                                 String methodName, Class<?> ...args) throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);

        assertThat(pointcut.getClassFilter().matches(clazz) &&
                pointcut.getMethodMatcher().matches(clazz.getMethod(methodName, args), null))
                .isEqualTo(expected);
    }
}
