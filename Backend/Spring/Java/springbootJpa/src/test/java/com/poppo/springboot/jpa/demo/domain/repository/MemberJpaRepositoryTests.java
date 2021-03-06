package com.poppo.springboot.jpa.demo.domain.repository;

import com.poppo.springboot.jpa.demo.domain.Member;
import com.poppo.springboot.jpa.demo.domain.repository.member.MemberJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTests {

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    public void basicCrud() {

        Member member1 = Member.builder().username("member1").build();
        Member member2 = Member.builder().username("member2").build();

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // 단건 조회 검증
        Member foundMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member foundMember2 = memberJpaRepository.findById(member2.getId()).get();
        assertThat(foundMember1).isEqualTo(member1);
        assertThat(foundMember2).isEqualTo(member2);

        // 콜렉션 조회 검증
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        // 카운트 검증
        Long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        Long deletedCount = memberJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThen() {
        Member member = Member.builder().username("AAA").age(20).build();
        Member member2 = Member.builder().username("AAA").age(30).build();
        memberJpaRepository.save(member);
        memberJpaRepository.save(member2);

        List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThan("AAA", 25);

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(30);
    }

    @Test
    public void paging() {

        memberJpaRepository.save(Member.builder().username("1").age(10).build());
        memberJpaRepository.save(Member.builder().username("2").age(10).build());
        memberJpaRepository.save(Member.builder().username("3").age(10).build());
        memberJpaRepository.save(Member.builder().username("4").age(10).build());
        memberJpaRepository.save(Member.builder().username("5").age(10).build());

        int age = 10;
        int offset = 0;
        int limit = 3;

        List<Member> pagedMember = memberJpaRepository.findByAgePaging(age, offset, limit);
        Long totalCount = memberJpaRepository.totalCount(age);

        assertThat(pagedMember.get(0).getUsername()).isEqualTo("1");
        assertThat(pagedMember.get(1).getUsername()).isEqualTo("2");
        assertThat(pagedMember.get(2).getUsername()).isEqualTo("3");

        assertThat(totalCount).isEqualTo(5);
    }

    @Test
    public void bulkUpdateQuery() {
        memberJpaRepository.save(Member.builder().username("1").age(10).build());
        memberJpaRepository.save(Member.builder().username("2").age(11).build());
        memberJpaRepository.save(Member.builder().username("3").age(12).build());
        memberJpaRepository.save(Member.builder().username("4").age(13).build());
        memberJpaRepository.save(Member.builder().username("5").age(14).build());

        int resultCount = memberJpaRepository.bulkAgePlus(11);

        assertThat(resultCount).isEqualTo(4);
    }
}