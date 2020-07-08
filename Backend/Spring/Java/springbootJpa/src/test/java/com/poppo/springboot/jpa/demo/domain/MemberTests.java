package com.poppo.springboot.jpa.demo.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTests {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void createTeamAndMapMember() {

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        entityManager.persist(teamA);
        entityManager.persist(teamB);

        Member member1 = new Member("member1", 31, teamA);
        Member member2 = new Member("member2", 32, teamA);
        Member member3 = new Member("member3", 33, teamB);
        Member member4 = new Member("member4", 34, teamB);

        entityManager.persist(member1);
        entityManager.persist(member2);
        entityManager.persist(member3);
        entityManager.persist(member4);

        entityManager.flush();

        List<Member> members = entityManager.createQuery("select m from Member m", Member.class).getResultList();

        members.forEach(m -> {
            System.out.println("member: " + m);
            System.out.println("member->team: " + m.getTeam());
        });
    }
}