package com.poppo.spring.jpa;

import com.poppo.spring.jpa.entity.Member;
import com.poppo.spring.jpa.entity.Team;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static com.poppo.spring.jpa.entity.QMember.*;

@SpringBootTest
@Transactional
public class QueryDslBasicTests {

    @Autowired
    private EntityManager entityManager;

    private JPAQueryFactory jpaQueryFactory;

    @BeforeEach
    public void setup() {

        // Test용 데이터 셋업
        Team teamA = Team.builder().name("teamA").build();
        Team teamB = Team.builder().name("teamB").build();
        entityManager.persist(teamA);
        entityManager.persist(teamB);

        Member member1 = Member.builder().username("member1").age(10).team(teamA).build();
        Member member2 = Member.builder().username("member2").age(20).team(teamA).build();
        Member member3 = Member.builder().username("member3").age(30).team(teamB).build();
        Member member4 = Member.builder().username("member4").age(40).team(teamB).build();

        entityManager.persist(member1);
        entityManager.persist(member2);
        entityManager.persist(member3);
        entityManager.persist(member4);

        // 동시성 문제는 걱정 안해도 된다는데? -> Spring에서 DI 해주면서 처리
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Test
    public void startWithJpql() {

        // 쿼리가 생 String이기 때문에 위험할 수 있다.
        Member foundByJpql = entityManager.createQuery(
                "select m from Member m where m.username = :username",
                Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertThat(foundByJpql.getUsername()).isEqualTo("member1");
    }

    @Test
    public void changeToQueryDsl() {
        // Parameter binding 없이 그냥 자바를 넘겨도 된다.
        Member foundMember = jpaQueryFactory.select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        assertThat(foundMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void sort() {
        entityManager.persist(new Member(null, 100));
        entityManager.persist(new Member("member5", 100));
        entityManager.persist(new Member("member6", 100));

        List<Member> result = jpaQueryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        assertThat(result.get(0).getUsername()).isEqualTo("member5");
        assertThat(result.get(1).getUsername()).isEqualTo("member6");
        assertThat(result.get(2).getUsername()).isNull();
    }

    @Test
    public void paging1() {
        List<Member> result = jpaQueryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void paging2() {
        QueryResults<Member> memberQueryResults = jpaQueryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();

        assertThat(memberQueryResults.getTotal()).isEqualTo(4);
        assertThat(memberQueryResults.getLimit()).isEqualTo(2);
        assertThat(memberQueryResults.getOffset()).isEqualTo(1);
        assertThat(memberQueryResults.getResults().size()).isEqualTo(2);
    }
}
