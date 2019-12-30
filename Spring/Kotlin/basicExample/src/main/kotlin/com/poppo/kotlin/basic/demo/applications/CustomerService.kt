package com.poppo.kotlin.basic.demo.applications

import com.poppo.kotlin.basic.demo.domain.Customer
import com.poppo.kotlin.basic.demo.domain.CustomerRepository
import com.poppo.kotlin.basic.demo.exceptions.CustomerNotFoundException
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class CustomerService(private val customerRepository: CustomerRepository) {

    fun getCustomers(): List<Customer> = customerRepository.findAll()

    fun getCustomer(id: Long) = customerRepository.runCatching { getOne(id) }
            .onFailure { throw CustomerNotFoundException("Customer Not Found") }
            .getOrThrow()

    fun createCustomer(customerName: String) = customerRepository.save(Customer(0, customerName))

    fun deleteCustomer(customerId: Long) {

        this.getCustomer(customerId)

        customerRepository.deleteById(customerId)
    }

    fun changeName(customerId: Long, customerName: String) {

        val customer = this.getCustomer(customerId)
        customer.name = customerName
        customerRepository.save(customer)
    }
}
