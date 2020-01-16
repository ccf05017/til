package com.poppo.spring.jpa.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class HelloTests {

    // QueryDsl 설정이 제대로 됐는지 확인하는 테스트 코드

    // @PersistenceContext <- 최근엔 이거 잘 안 쓴다
    @Autowired
    private EntityManager entityManager;

    @Test
    public void queryDslTest() {

        Hello hello = new Hello();
        entityManager.persist(hello);

        JPAQueryFactory query = new JPAQueryFactory(entityManager);
        QHello qHello = new QHello("h");

        Hello result = query.selectFrom(qHello).fetchOne();

        assertThat(hello).isEqualTo(result);
        assertThat(hello.getId()).isEqualTo(result.getId());
    }
}