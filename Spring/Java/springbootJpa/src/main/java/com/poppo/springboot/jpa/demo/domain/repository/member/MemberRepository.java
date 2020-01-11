package com.poppo.springboot.jpa.demo.domain.repository.member;

import com.poppo.springboot.jpa.demo.domain.Member;
import com.poppo.springboot.jpa.demo.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

// 이 인터페이스 설정을 보고 실제 구현체를 내부에서 만들어준다.
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    // 쿼리 메서드 기능 (앵간한 걸 이게 다 해준다)
    // 단점: 딱 정해진 것만 사용해야 함, 조건이 많으면 더럽게 길어짐
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    // 기본적으로 JPQL을 사용하게 된다.
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);   // parameter name 바인딩

    // 이런 식으로 단순 속성을 갖고 오는 것도 가능하다.
    @Query("select m.username from Member m")
    List<String> findUsernameList();

    // DTO를 바로 조회하는 법
    @Query("select new com.poppo.springboot.jpa.demo.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m join fetch m.team t")
    List<Member> optimizedFindMember();

    // 콜렉션 파라미터: 굉장히 자주 사용할 수 있을 기능
    // List 리턴 타입은 절대 null 나오지 않는다
    // 비었으면 비었지 null은 아님
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    // Optional 타입도 당연히 사용가능하다.
    Optional<Member> findByUsername(String username);

    // count 최적화가 필요한 겅우에는 count query를 분리해주는 것도 방법이다.
    @Query(
            value = "select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m"
    )
    Page<Member> findByAge(int age, Pageable pageable);

    Slice<Member> findSlicedByAge(int age, Pageable pageable);

    // Top 조회 같은 것도 가능
    List<Member> findTopByAge(int age);

    @Modifying(clearAutomatically = true)      // 이게 없으면 반환값이 이상해진다.
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    // graph 최적화 쿼리
    // 이렇게 하면 프록시 객체가 아니라 실제 객체가 나와서 붙는다. 챡!
    // 근데 매번 하기는 귀찮다.. => Entity Graph!
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})     // 더 추가된다면 dict 안에 쭉쭉 추가해라
    List<Member> findAll();

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);
}
