package com.poppo.kotlin.basic.demo.domain

import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, Long> {

}
