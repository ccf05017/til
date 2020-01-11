package com.poppo.springboot.jpa.demo.domain.repository.member;

import com.poppo.springboot.jpa.demo.domain.Member;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

// 인터페이스 이름은 아무렇게나 지어도 된다.
// 하지만 구현체의 이름은 원하는 리포지토리 인터페이스 이름 뒤에 Impl 붙이는 형태로 작성해야 한다.
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public List<Member> findMemberCustom() {
        return entityManager.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
