package com.poppo.kotlin.basic.demo.applications

import com.poppo.kotlin.basic.demo.domain.Customer
import com.poppo.kotlin.basic.demo.domain.CustomerRepository
import com.poppo.kotlin.basic.demo.exceptions.CustomerIdDuplicatedException
import com.poppo.kotlin.basic.demo.exceptions.CustomerNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomerService(@Autowired val customerRepository: CustomerRepository) {

    fun getCustomers(): List<Customer> {

        return customerRepository.findAll()
    }

    fun getCustomer(id: Int): Customer? {

        return customerRepository.findById(id) ?: throw CustomerNotFoundException("Customer Not Found")
    }

    fun createCustomer(customerId: Int, customerName: String): Customer {

        if (customerRepository.findById(customerId) != null)
            throw CustomerIdDuplicatedException("Customer Id Duplicated")

        val saved = Customer(customerId, customerName)
        customerRepository.createCustomer(saved)

        return saved
    }

    fun deleteCustomer(customerId: Int) {

        this.getCustomer(customerId)

        customerRepository.deleteCustomer(customerId)
    }

    fun updateCustomer(targetId: Int, inputId: Int, inputName: String) {

        this.getCustomer(targetId)
        customerRepository.updateCustomer(targetId, Customer(inputId, inputName))
    }
}