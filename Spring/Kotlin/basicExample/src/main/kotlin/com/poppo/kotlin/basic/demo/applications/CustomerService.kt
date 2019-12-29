package com.poppo.kotlin.basic.demo.applications

import com.poppo.kotlin.basic.demo.domain.Customer
import com.poppo.kotlin.basic.demo.domain.CustomerRepository
import com.poppo.kotlin.basic.demo.exceptions.CustomerNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomerService(@Autowired private val customerRepository: CustomerRepository) {

    fun getCustomers() = customerRepository.findAll()

    fun getCustomer(id: Long) =
            customerRepository.findCustomerById(id)
                    ?: throw CustomerNotFoundException("Customer Not Found")

    fun createCustomer(customerName: String) =
            customerRepository.save(Customer(0, customerName))

    fun deleteCustomer(customerId: Long) {

        this.getCustomer(customerId)

        customerRepository.deleteById(customerId)
    }

    fun updateCustomerName(customerId: Long, customerName: String) {

        val customer = this.getCustomer(customerId)
        customer.name = customerName
        customerRepository.save(customer)
    }
}
