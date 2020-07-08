package com.poppo.kotlin.basic.demo.dtos

data class CustomerRequestDto(var name: String)

data class CustomerResponseDto(var id: Long, var name: String)