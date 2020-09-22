package com.poppo.querydsl.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
@Transactional
class MemberTest {
    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void testEntity() {
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

        entityManager.flush();
        entityManager.clear();

        List<Member> members = entityManager.createQuery("select m from Member m", Member.class)
                .getResultList();

        for (Member member: members) {
            System.out.println("member = " + member);
            System.out.println("-> member.team" + member.getTeam());
        }
    }
}
