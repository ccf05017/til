package com.poppo.kotlin.basic.demo.controllers

import com.poppo.kotlin.basic.demo.applications.CustomerService
import com.poppo.kotlin.basic.demo.domain.Customer
import com.poppo.kotlin.basic.demo.dtos.CustomerRequestDto
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
    fun getCustomer(@PathVariable id: Long): Customer? {

        return customerService.getCustomer(id)
    }

    @PostMapping("/customers")
    fun createCustomer(@RequestBody customer: CustomerRequestDto): ResponseEntity<String> {

        val saved = customerService.createCustomer(customer.name)

        return ResponseEntity
                .created(URI("/customers/${saved.id}"))
                .body("{}")
    }

    @DeleteMapping("/customers/{id}")
    fun deleteCustomer(@PathVariable id: Long) {

        customerService.deleteCustomer(id)
    }

    @PatchMapping("/customers/{id}")
    fun updateCustomer(@RequestBody customer: CustomerRequestDto,
                       @PathVariable id: Long) {

        customerService.updateCustomer(id, customer.name)
    }
}
