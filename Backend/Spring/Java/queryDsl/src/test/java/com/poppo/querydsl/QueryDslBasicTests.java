package com.poppo.querydsl;

import com.poppo.querydsl.entity.Member;
import com.poppo.querydsl.entity.QMember;
import com.poppo.querydsl.entity.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class QueryDslBasicTests {
    @PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    public void setup() {
        Team team = new Team("teamA");
        Team teamB = new Team("teamB");

        entityManager.persist(team);
        entityManager.persist(teamB);

        Member member1 = new Member("member1", 10, team);
        Member member2 = new Member("member2", 20, team);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        entityManager.persist(member1);
        entityManager.persist(member2);
        entityManager.persist(member3);
        entityManager.persist(member4);
    }

    @Test
    public void jpqlExample() {
        // find member1
        String queryString = "select m from Member m " +
                "where m.username = :username";

        Member member = entityManager.createQuery(queryString, Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertThat(member.getUsername()).isEqualTo("member1");
    }

    @Test
    public void queryDslExample() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QMember m = new QMember("m");

        Member foundMember = queryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("member1"))
                .fetchOne();

        assertThat(foundMember.getUsername()).isEqualTo("member1");
    }
}
