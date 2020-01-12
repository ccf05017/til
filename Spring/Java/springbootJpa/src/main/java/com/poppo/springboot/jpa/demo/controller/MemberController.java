package com.poppo.springboot.jpa.demo.controller;

import com.poppo.springboot.jpa.demo.domain.Member;
import com.poppo.springboot.jpa.demo.domain.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {

        Member found = memberRepository.findById(id).get();

        return found.getUsername();
    }

    // Domain Class Converter
    // 막상 자주 사용하지는 않는다.
    // 트랜잭션 범위도 아니기 때문에 여기서 가져온 객체를 수정하거나 하지도 못한다.
    @GetMapping("/memberEntities/{id}")
    public String findMemberEntity(@PathVariable("id") Member member) {

        return member.getUsername();
    }


    @PostConstruct
    public void init() {
        memberRepository.save(Member.builder().username("memberA").build());
    }
}
