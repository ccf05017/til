package com.poppo.springboot.jpa.demo.domain.repository;

import com.poppo.springboot.jpa.demo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// 이 인터페이스 설정을 보고 실제 구현체를 내부에서 만들어준다.
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 쿼리 메서드 기능 (앵간한 걸 이게 다 해준다)
    // 단점: 딱 정해진 것만 사용해야 함, 조건이 많으면 더럽게 길어짐
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    // 기본적으로 JPQL을 사용하게 된다.
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);
}
