package com.poppo.querydsl.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.poppo.querydsl.dto.QQueryProjectionMemberDto is a Querydsl Projection type for QueryProjectionMemberDto
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QQueryProjectionMemberDto extends ConstructorExpression<QueryProjectionMemberDto> {

    private static final long serialVersionUID = -872628789L;

    public QQueryProjectionMemberDto(com.querydsl.core.types.Expression<String> username, com.querydsl.core.types.Expression<Integer> age) {
        super(QueryProjectionMemberDto.class, new Class<?>[]{String.class, int.class}, username, age);
    }

}

