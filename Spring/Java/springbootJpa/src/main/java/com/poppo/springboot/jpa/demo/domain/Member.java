package com.poppo.springboot.jpa.demo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(of = {"id", "username", "age"})           // toString 찍을 때 연관관계는 앵간하면 빼자(잘못 하면 무한루프)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username, int age, Team team) {

        this.username = username;
        this.age = age;

        if (team != null) {
            changeTeam(team);
        }
    }

    public void insertFirstTeam(Team team) {

        this.team = team;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

    public void changeName(String username) {
        this.username = username;
    }
}
