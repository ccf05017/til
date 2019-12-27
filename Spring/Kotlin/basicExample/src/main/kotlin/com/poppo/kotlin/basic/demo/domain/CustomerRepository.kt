package com.poppo.kotlin.basic.demo.domain

interface CustomerRepository {

    fun findAll() : List<Customer>

    fun findById(id : Int) : Customer?

    fun createCustomer(customer: Customer)

    fun updateCustomer(id: Int, customer: Customer)

    fun deleteCustomer(id: Int)
}