package com.poppo.kotlin.basic.demo.controllers

import com.poppo.kotlin.basic.demo.applications.CustomerService
import com.poppo.kotlin.basic.demo.domain.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
class CustomerController(@Autowired val customerService: CustomerService) {

    @GetMapping("/customers")
    fun getAllCustomers() : List<Customer> {

        return customerService.getCustomers()
    }

    @GetMapping("/customers/{id}")
    fun getCustomer(@PathVariable id: Int): Customer? {

        return customerService.getCustomer(id)
    }

    @PostMapping("/customers")
    fun createCustomer(@RequestBody customer: Customer): ResponseEntity<String> {

        val saved = customerService.createCustomer(customer.id, customer.name)
        val url = "/customers/${saved.id}"

        return ResponseEntity.created(URI(url)).body("{}")
    }

    @DeleteMapping("/customers/{id}")
    fun deleteCustomer(@PathVariable id: Int) {

        customerService.deleteCustomer(id)
    }

    @PatchMapping("/customers/{id}")
    fun updateCustomer(@RequestBody customer: Customer,
                       @PathVariable id: Int) {

        customerService.updateCustomer(id, customer.id, customer.name)
    }
}