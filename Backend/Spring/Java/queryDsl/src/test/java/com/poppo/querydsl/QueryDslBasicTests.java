package com.poppo.querydsl;

import com.poppo.querydsl.entity.Member;
import com.poppo.querydsl.entity.Team;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.poppo.querydsl.entity.QMember.member;
import static com.poppo.querydsl.entity.QTeam.team;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class QueryDslBasicTests {
    @PersistenceContext
    EntityManager entityManager;

    JPAQueryFactory jpaQueryFactory;

    @BeforeEach
    public void setup() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);

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
        // QMember m = new QMember("m");
        // QMember m = QMember.member; // 이걸 static import로 바꿔서 사용하는 방법도 있다.

        Member foundMember = jpaQueryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        assertThat(foundMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void searchExample() {
        Member foundMember = jpaQueryFactory
                .selectFrom(member)
                .where(member.username.eq("member1")
                        .and(member.age.eq(10)))
                .fetchOne();

        assertThat(foundMember.getUsername()).isEqualTo("member1");

        Member notFoundMember = jpaQueryFactory
                .selectFrom(member)
                .where(member.username.eq("member1")
                        .and(member.age.eq(11)))
                .fetchOne();

        assertThatThrownBy(() -> notFoundMember.getUsername()).isInstanceOf(NullPointerException.class);
    }

    // where 절에 파라미터 형태로 조건을 던져서 and 쿼리르 작성할 수 있다.(or는 안된다)
    @Test
    public void searchAndParam() {
        Member foundMember = jpaQueryFactory
                .selectFrom(member)
                .where(
                        member.username.eq("member1"),
                        member.age.eq(10)
                )
                .fetchOne();

        assertThat(foundMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void getResultTest() {
//        List<Member> members = jpaQueryFactory
//                .selectFrom(member)
//                .fetch();
//
//        Member fetchOne = jpaQueryFactory
//                .selectFrom(QMember.member)
//                .fetchOne();
//
//        Member fetchFirst = jpaQueryFactory
//                .selectFrom(QMember.member)
//                .fetchFirst(); // limit(1).fetchOne()

        QueryResults<Member> results = jpaQueryFactory
                .selectFrom(member)
                .fetchResults();

        long total = results.getTotal();
        List<Member> content = results.getResults();
        assertThat(total).isEqualTo(4);
        assertThat(content.get(0).getUsername()).isEqualTo("member1");

        long totalCount = jpaQueryFactory
                .selectFrom(member)
                .fetchCount();
        assertThat(totalCount).isEqualTo(4);
    }

    @Test
    public void sort() {
        // 나이 내림차순
        // 이름 올림차순
        // 이름이 없으면 마지막에 출력
        entityManager.persist(new Member(null, 100));
        entityManager.persist(new Member("member5", 100));
        entityManager.persist(new Member("member6", 100));

        List<Member> members = jpaQueryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        assertThat(members.get(0).getUsername()).isEqualTo("member5");
        assertThat(members.get(1).getUsername()).isEqualTo("member6");
        assertThat(members.get(2).getUsername()).isNull();
    }

    @Test
    public void pagingTest() {
        List<Member> members = jpaQueryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        assertThat(members.size()).isEqualTo(2);
        assertThat(members.get(0).getUsername()).isEqualTo("member3");
    }

    @Test
    public void pagingWithCountTest() {
        QueryResults<Member> memberQueryResults = jpaQueryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();

        assertThat(memberQueryResults.getTotal()).isEqualTo(4);
        assertThat(memberQueryResults.getResults().get(0).getUsername()).isEqualTo("member3");
    }

    @Test
    public void aggregationTest() {
        // DDD aggregate와는 전혀 다르다 헷갈리지 말자
        List<Tuple> aggregation = jpaQueryFactory
                .select(
                        member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min()
                )
                .from(member)
                .fetch();

        Tuple tuple = aggregation.get(0);
        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
        assertThat(tuple.get(member.age.avg())).isEqualTo(25);
        assertThat(tuple.get(member.age.max())).isEqualTo(40);
        assertThat(tuple.get(member.age.min())).isEqualTo(10);
    }

    @Test
    public void groupByTest() {
        // 팀의 이르과 각 팀의 평균 연령을 구하기
        List<Tuple> result = jpaQueryFactory
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(15);
        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(35);
    }
}
