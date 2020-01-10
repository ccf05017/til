package com.poppo.springboot.jpa.demo.domain.repository;

import com.poppo.springboot.jpa.demo.domain.Member;
import com.poppo.springboot.jpa.demo.domain.Team;
import com.poppo.springboot.jpa.demo.dto.MemberDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberRepositoryTests {

    // Proxy 객체를 만들어서 꽂아준다.
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @PersistenceContext
    EntityManager entityManager;

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

    @Test
    public void getAllUsernames() {

        Member member = Member.builder().username("AAA").age(20).build();
        Member member2 = Member.builder().username("BBB").age(30).build();
        memberRepository.save(member);
        memberRepository.save(member2);

        List<String> usernameList = memberRepository.findUsernameList();

        assertThat(usernameList.get(0)).isEqualTo("AAA");
    }

    @Test
    public void getDtoDirectly() {

        Team team = Team.builder().name("teamA").build();
        teamRepository.save(team);

        Member member = Member.builder().username("AAA").age(10).build();
        member.insertFirstTeam(team);
        memberRepository.save(member);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        assertThat(memberDto.get(0).getId()).isEqualTo(1L);
        assertThat(memberDto.get(0).getUsername()).isEqualTo("AAA");
        assertThat(memberDto.get(0).getTeamName()).isEqualTo("teamA");
    }

    @Test
    public void xToOneOptimizedQueryTest() {

        Team team = Team.builder().name("teamA").build();
        teamRepository.save(team);

        Member member = Member.builder().username("AAA").age(10).build();
        member.insertFirstTeam(team);

        Member member2 = Member.builder().username("BBB").age(20).build();
        member2.insertFirstTeam(team);
        memberRepository.save(member);
        memberRepository.save(member2);

        List<Member> members = memberRepository.optimizedFindMember();
        assertThat(members.get(0).getTeam().getName()).isEqualTo("teamA");
        assertThat(members.get(1).getTeam().getName()).isEqualTo("teamA");
    }

    @Test
    public void testFindByNames() {

        Member member = Member.builder().username("AAA").age(10).build();
        Member member2 = Member.builder().username("BBB").age(20).build();
        memberRepository.save(member);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));

        assertThat(members.get(0).getAge()).isEqualTo(10);
        assertThat(members.get(1).getAge()).isEqualTo(20);
    }

    @Test
    public void optionalReturn() {

        Member member = Member.builder().username("AAA").age(10).build();
        memberRepository.save(member);

        Optional<Member> correctFound = memberRepository.findByUsername("AAA");
        Optional<Member> incorrectFound = memberRepository.findByUsername("AAA2");

        assertThat(correctFound.orElse(null)).isNotNull();
        assertThat(incorrectFound.orElse(null)).isNull();
    }

    @Test
    public void paging() {

        memberRepository.save(Member.builder().username("1").age(10).build());
        memberRepository.save(Member.builder().username("2").age(10).build());
        memberRepository.save(Member.builder().username("3").age(10).build());
        memberRepository.save(Member.builder().username("4").age(10).build());
        memberRepository.save(Member.builder().username("5").age(10).build());

        int age = 10;
        PageRequest pageRequest = PageRequest.of(1, 3, Sort.by(Sort.Direction.ASC, "username"));

        Page<Member> pagedMember = memberRepository.findByAge(age, pageRequest);

        Slice<Member> slicedMember = memberRepository.findSlicedByAge(age, pageRequest);

        List<Member> members = pagedMember.getContent();

        assertThat(members.get(0).getUsername()).isEqualTo("4");
        assertThat(members.get(1).getUsername()).isEqualTo("5");

        // Page 인터페이스에서 제공하는 다양한 기능들
        assertThat(pagedMember.getTotalElements()).isEqualTo(5);
        assertThat(pagedMember.getNumber()).isEqualTo(1);
        assertThat(pagedMember.getTotalPages()).isEqualTo(2);
        assertThat(pagedMember.isFirst()).isFalse();
        assertThat(pagedMember.hasNext()).isFalse();

        // Slice 인터페이스에서 제공하는 기능들 (모바일에서 주로 사용된다)
        assertThat(slicedMember.getNumber()).isEqualTo(1);
        assertThat(slicedMember.isFirst()).isFalse();
        assertThat(slicedMember.hasNext()).isFalse();
        // Page와 다르게 제공되지 않는 기능
//        assertThat(slicedMember.getTotalPages()).isEqualTo(2);
//        assertThat(slicedMember.getTotalElements()).isEqualTo(5);
    }

    @Test
    public void pagingToDto() {
        memberRepository.save(Member.builder().username("1").age(10).build());
        memberRepository.save(Member.builder().username("2").age(10).build());
        memberRepository.save(Member.builder().username("3").age(10).build());
        memberRepository.save(Member.builder().username("4").age(10).build());
        memberRepository.save(Member.builder().username("5").age(10).build());

        int age = 10;
        PageRequest pageRequest = PageRequest.of(1, 3, Sort.by(Sort.Direction.ASC, "username"));

        Page<Member> pagedMember = memberRepository.findByAge(age, pageRequest);

        Page<MemberDto> memberDtos
                = pagedMember.map(member -> new MemberDto(member.getId(), member.getUsername(), null));

        assertThat(memberDtos.getContent().get(0).getUsername()).isEqualTo("4");
    }

    @Test
    public void bulkUpdateQuery() {

        memberRepository.save(Member.builder().username("1").age(10).build());
        memberRepository.save(Member.builder().username("2").age(11).build());
        memberRepository.save(Member.builder().username("3").age(12).build());
        memberRepository.save(Member.builder().username("4").age(13).build());
        memberRepository.save(Member.builder().username("5").age(14).build());

        int resultCount = memberRepository.bulkAgePlus(11);

        Optional<Member> member5 = memberRepository.findByUsername("5");

        assertThat(resultCount).isEqualTo(4);
        // bulk 연산은 생 쿼리가 날라가기 때문에
        // 영속성 컨텍스트에 반영이 바로 안된다.
        // Modifying 어노테이션에 clearAutomatically 옵션을 설정해주면 바로 반영된다.
        // 이딴 걸 하느니 사실 트랜잭션이 bulk 처리만 하고 끝나도록 설계하는 게 가장 현-명
        assertThat(member5.orElse(null).getAge()).isEqualTo(15);
    }

    @Test
    public void entityGraph() {
        Team teamA = Team.builder().name("teamA").build();
        Team teamB = Team.builder().name("teamB").build();
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = Member.builder().username("member1").age(10).team(teamA).build();
        Member member2 = Member.builder().username("member2").age(20).team(teamB).build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        entityManager.flush();
        entityManager.clear();

        // 이걸 불러봤자 team을 불러오진 않는다.
        // 필요에 의해서 요청할 때 쿼리가 나감.
        // 문제는 XtoOne 관계이기 때문에 다른 팀을 요청할 때마다 새 쿼리가 나간다.
        List<Member> members = memberRepository.findAll();

        // 그러니 이걸 씁시다 => 하지만 매번 JPQL 쓰기 귀찮으니까 EntityGraph를 씁시다
        List<Member> memberFetchJoin = memberRepository.findMemberFetchJoin();

        // 아예 member 부를 때 team을 같이 불러준다(proxy 객체가 아니라 실제 객체로)
        assertThat(members.get(0).getTeam().getName()).isEqualTo("teamA");
        assertThat(members.get(1).getTeam().getName()).isEqualTo("teamB");
    }

    @Test
    public void queryHint() {
        
    }
}
