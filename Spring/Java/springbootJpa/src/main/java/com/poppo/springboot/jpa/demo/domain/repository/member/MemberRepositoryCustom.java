package com.poppo.springboot.jpa.demo.domain.repository.member;

import com.poppo.springboot.jpa.demo.domain.Member;

import java.util.List;

public interface MemberRepositoryCustom {

    // 사용을 권장하진 않는다.
    // 화면에 묶인 복잡한 리포지들은 그냥 아예 클래스를 쪼개 버리는 게 더 낫다.
    List<Member> findMemberCustom();
}
