package com.poppo.springboot.jpa.demo.controller;

import com.poppo.springboot.jpa.demo.domain.Member;
import com.poppo.springboot.jpa.demo.domain.Team;
import com.poppo.springboot.jpa.demo.domain.repository.TeamRepository;
import com.poppo.springboot.jpa.demo.domain.repository.member.MemberRepository;
import com.poppo.springboot.jpa.demo.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {

        Member found = memberRepository.findById(id).get();

        return found.getUsername();
    }

    // Domain Class Converter
    // 막상 자주 사용하지는 않는다.
    // 트랜잭션 위도 아니기 때문에 여기서 가져온 객체를 수정하거나 하지도 못한다.
    @GetMapping("/memberEntities/{id}")
    public String findMemberEntity(@PathVariable("id") Member member) {

        return member.getUsername();
    }

    // PageRequest가 DI로 들어옴
    // 만약 페이지를 1부터 보내고 싶다? -> 직접 구현해라
    @GetMapping("/members")
    public Page<MemberDto> getList(Pageable pageable) {

        return memberRepository.findAll(pageable)
                .map(member -> MemberDto.builder()
                        .id(member.getId())
                        .username(member.getUsername())
                        .teamName(member.getTeam().getName())
                        .build()
                );
    }

    @PostConstruct
    public void init() {

        Team teamA = teamRepository.save(Team.builder().name("teamA").build());

        IntStream.rangeClosed(0, 100).forEach(
                number -> memberRepository.save(Member.builder().username("member" + number).team(teamA).build())
        );
    }
}
