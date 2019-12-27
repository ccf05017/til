package com.poppo.kotlin.basic.demo.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CustomerRepositoryImplTests {

    private val customerRepository: CustomerRepository = CustomerRepositoryImpl()

    @Test
    fun `Find all customers`() {

        val customers = customerRepository.findAll()

        assertThat(customers[0].name).isEqualTo("poppo")
    }

    @Test
    fun `Find one customer by id`() {

        val customer = customerRepository.findById(1)

        assertThat(customer?.name).isEqualTo("poppo")
    }

    @Test
    fun `Add one customer`() {

        assertThat(customerRepository.findAll().size).isEqualTo(3)

        customerRepository.createCustomer(Customer(4, "new customer"))

        assertThat(customerRepository.findAll().size).isEqualTo(4)
    }

    @Test
    fun `Update one customer`() {

        assertThat(customerRepository.findById(1)?.name).isEqualTo("poppo")

        customerRepository.updateCustomer(1, Customer(1, "updated"))

        assertThat(customerRepository.findById(1)?.name).isEqualTo("updated")
    }

    @Test
    fun `Delete one customer`() {

        assertThat(customerRepository.findAll().size).isEqualTo(3)

        customerRepository.deleteCustomer(2)

        assertThat(customerRepository.findAll().size).isEqualTo(2)
    }
}