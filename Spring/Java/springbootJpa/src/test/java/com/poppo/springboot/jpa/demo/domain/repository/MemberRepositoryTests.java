package com.poppo.springboot.jpa.demo.domain.repository;

import com.poppo.springboot.jpa.demo.domain.Member;
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
public class MemberRepositoryTests {

    // Proxy 객체를 만들어서 꽂아준다.
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void whatRepositoryInterface() {

        System.out.println("MemberRepository Interface: " + memberRepository.getClass());
    }

    @Test
    public void basicCrud() {

        Member member1 = Member.builder().username("member1").build();
        Member member2 = Member.builder().username("member2").build();

        memberRepository.save(member1);
        memberRepository.save(member2);

        // 단건 조회 검증
        Member foundMember1 = memberRepository.findById(member1.getId()).get();
        Member foundMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(foundMember1).isEqualTo(member1);
        assertThat(foundMember2).isEqualTo(member2);

        // 콜렉션 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        // 카운트 검증
        Long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        Long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThen() {
        Member member = Member.builder().username("AAA").age(20).build();
        Member member2 = Member.builder().username("AAA").age(30).build();
        memberRepository.save(member);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 25);

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(30);
    }

    @Test
    public void jpqlTest() {
        Member member = Member.builder().username("AAA").age(20).build();
        memberRepository.save(member);

        List<Member> result = memberRepository.findUser("AAA", 20);

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
    }
}
