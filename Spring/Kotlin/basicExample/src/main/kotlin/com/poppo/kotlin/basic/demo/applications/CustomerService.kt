package com.poppo.kotlin.basic.demo.applications

import com.poppo.kotlin.basic.demo.domain.Customer
import com.poppo.kotlin.basic.demo.domain.CustomerRepository
import com.poppo.kotlin.basic.demo.exceptions.CustomerNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomerService(@Autowired val customerRepository: CustomerRepository) {

    fun getCustomers(): List<Customer> {

        return customerRepository.findAll()
    }

    fun getCustomer(id: Long): Customer? {

        return customerRepository.findCustomerById(id) ?: throw CustomerNotFoundException("Customer Not Found")
    }

    fun createCustomer(customerName: String): Customer {

        return customerRepository.save(Customer(customerName))
    }

    fun deleteCustomer(customerId: Long) {

        this.getCustomer(customerId)

        customerRepository.deleteById(customerId)
    }

    fun updateCustomer(customerId: Long, customerName: String) {

        val customer = this.getCustomer(customerId)
        customer?.name = customerName
        customerRepository.save(customer!!)
    }
}