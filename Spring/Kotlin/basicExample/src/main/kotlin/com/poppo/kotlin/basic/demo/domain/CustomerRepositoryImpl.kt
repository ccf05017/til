package com.poppo.kotlin.basic.demo.domain

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class CustomerRepositoryImpl : CustomerRepository {

    companion object {
        val initialCustomers = arrayOf(
                Customer(1, "poppo"),
                Customer(2, "saul"),
                Customer(3, "ita")
        )
    }

    val customers = ConcurrentHashMap<Int, Customer>(initialCustomers.associateBy(Customer::id))

    override fun findAll(): List<Customer> {

        return customers.map(Map.Entry<Int, Customer>::value).toList()
    }

    override fun findById(id: Int): Customer? {

        return customers[id]
    }

    override fun createCustomer(customer: Customer) {

        customers[customer.id] = customer
    }

    override fun updateCustomer(id: Int, customer: Customer) {

        customers.remove(id)
        customers[id] = customer
    }

    override fun deleteCustomer(id: Int) {

        customers.remove(id)
    }
}