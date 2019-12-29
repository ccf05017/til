package com.poppo.kotlin.basic.demo.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Customer(
        @Id
        @GeneratedValue
        var id: Long,

        var name: String
)
