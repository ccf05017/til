package com.poppo.springboot.jpa.demo.domain.repository.member;

import com.poppo.springboot.jpa.demo.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

// 순수 JPA 리포지토리
@Repository     // component scan + JPA 에러를 Spring 에러로 전환해서 처리 가능하도록 바꿔줌
public class MemberJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Member save(Member member) {
        entityManager.persist(member);
        return member;
    }

    public void delete(Member member) {
        entityManager.remove(member);
    }

    public List<Member> findAll() {

        return entityManager.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Optional<Member> findById(Long id) {

        Member member = entityManager.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public Long count() {

        return entityManager.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult();
    }

    public Member find(Long id) {

        return entityManager.find(Member.class, id);
    }

    // 테스트용 구현 메서드 -> 이런걸 쿼리메서드로 할 수 있다!
    public List<Member> findByUsernameAndAgeGreaterThan(String username, int age) {

        return entityManager.createQuery("select m from Member m " +
                "where m.username = :username and m.age > :age",
                Member.class)
                .setParameter("username", username)
                .setParameter("age", age)
                .getResultList();
    }

    public List<Member> findByAgePaging(int age, int offset, int limit) {

        return entityManager.createQuery("select m from Member m" +
                " where m.age = :age" +
                " order by m.username asc", Member.class)
                .setParameter("age", age)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Long totalCount(int age) {

        return entityManager.createQuery("select count(m) from Member m where m.age = :age", Long.class)
                .setParameter("age", age)
                .getSingleResult();
    }

    public int bulkAgePlus(int age) {
        return entityManager.createQuery(
                "update Member m set m.age = m.age + 1" +
                        " where m.age >= :age")
                .setParameter("age", age)
                .executeUpdate();
    }
}
