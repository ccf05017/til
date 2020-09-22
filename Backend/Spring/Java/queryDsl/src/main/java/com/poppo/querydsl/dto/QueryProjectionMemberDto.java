package com.poppo.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class QueryProjectionMemberDto {
    private String username;
    private int age;

    @QueryProjection
    public QueryProjectionMemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
