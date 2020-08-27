package com.poppo.querydsl;

import com.poppo.querydsl.entity.Member;
import com.poppo.querydsl.entity.QMember;
import com.poppo.querydsl.entity.Team;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static com.poppo.querydsl.entity.QMember.member;
import static com.poppo.querydsl.entity.QTeam.team;
import static com.querydsl.jpa.JPAExpressions.select;
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

    @Test
    public void joinTest() {
        // 팀 A에 소속된 모든 회원 찾기
        List<Member> result = jpaQueryFactory
                .selectFrom(member)
                .join(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("member1", "member2");
    }

    @Test
    public void thetaJoinTest() {
        // 연관 관계가 전혀 없는 조인 걸기
        // 회원 이름과 팀 이름이 같은 회원 조회
        entityManager.persist(new Member("teamA"));
        entityManager.persist(new Member("teamB"));
        entityManager.persist(new Member("teamC"));

        List<Member> result = jpaQueryFactory
                .select(member)
                .from(member, team)
                .where(member.username.eq(team.name))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("teamA", "teamB");
    }

    @Test
    public void joinTargetFilteringTest() {
        // 회원과 팀을 조인하는데 팀 이름이 teamA인 팀만 가져오고, 회원은 모두 다 가져온다.
        List<Tuple> result = jpaQueryFactory
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team).on(team.name.eq("teamA"))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println(tuple);
        }
    }

    @Test
    public void nonRelationOuterJoinTest() {
        // 연관관계가 없는 엔티티를 외부 조인 시키기
        // 회원의 이름과 팀 이름이 같은 대상 외부 조인
        entityManager.persist(new Member("teamA"));
        entityManager.persist(new Member("teamB"));
        entityManager.persist(new Member("teamC"));

        List<Tuple> result = jpaQueryFactory
                .select(member, team)
                .from(member)
                .leftJoin(team).on(member.username.eq(team.name))
                .fetch();

        for(Tuple tuple : result) {
            System.out.println(tuple);
        }
    }

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    @Test
    public void fetchJoinTest() {
        entityManager.flush();
        entityManager.clear();

        // no fetch - member만 조회한다 (fetch Lazy이기 때문에 team은 안 가져온다)
        Member foundMember = jpaQueryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.username.eq("member1"))
                .fetchOne();

        boolean loaded = entityManagerFactory.getPersistenceUnitUtil().isLoaded(foundMember.getTeam());
        assertThat(loaded).isFalse();

        // fetch 적용
        entityManager.flush();
        entityManager.clear();

        Member foundMember2 = jpaQueryFactory
                .selectFrom(QMember.member)
                .join(member.team, team).fetchJoin()
                .where(QMember.member.username.eq("member1"))
                .fetchOne();

        boolean loaded2 = entityManagerFactory.getPersistenceUnitUtil().isLoaded(foundMember2.getTeam());
        assertThat(loaded2).isTrue();
    }

    @Test
    public void subQueryTest() {
        // 나이가 가장 많은 회원을 조회해보자
        QMember memberSub = new QMember("memberSub");

        List<Member> result = jpaQueryFactory
                .selectFrom(member)
                .where(member.age.eq(
                        select(memberSub.age.max())
                                .from(memberSub)
                ))
                .fetch();

        assertThat(result).extracting("age").containsExactly(40);
    }

    @Test
    public void subQueryTest2() {
        // 나이가 평균 이상인 회원을 검색해보자
        QMember memberSub = new QMember("memberSub");

        List<Member> result = jpaQueryFactory
                .selectFrom(member)
                .where(member.age.goe(
                        select(memberSub.age.avg())
                                .from(memberSub)
                ))
                .fetch();

        assertThat(result).extracting("age").containsExactly(30, 40);
    }

    @Test
    public void subQueryTest3() {
        // 나이가 평균 이상인 회원을 검새개해보자
        QMember memberSub = new QMember("memberSub");

        List<Member> result = jpaQueryFactory
                .selectFrom(member)
                .where(member.age.in(
                        select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))
                ))
                .fetch();

        assertThat(result).extracting("age").containsExactly(20, 30, 40);
    }

    @Test
    public void selectSubQueryTest() {
        QMember memberSub = new QMember("memberSub");

        List<Tuple> result = jpaQueryFactory
                .select(member.username,
                        select(memberSub.age.avg())
                                .from(memberSub)
                )
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println(tuple);
        }
    }

    @Test
    public void basicCaseTest() {
        List<String> result = jpaQueryFactory
                .select(member.age
                        .when(10).then("열짤")
                        .when(20).then("스무쨜")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println(s);
        }
    }

    @Test
    public void complexCaseTest() {
        List<String> result = jpaQueryFactory
                .select(new CaseBuilder()
                        .when(member.age.between(0, 20)).then("0~20")
                        .when(member.age.between(21, 230)).then("21~30")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println(s);
        }
    }
}
