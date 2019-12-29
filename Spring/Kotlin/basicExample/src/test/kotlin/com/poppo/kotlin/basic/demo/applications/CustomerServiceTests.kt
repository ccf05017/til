package com.poppo.kotlin.basic.demo.applications

import com.poppo.kotlin.basic.demo.domain.Customer
import com.poppo.kotlin.basic.demo.domain.CustomerRepository
import com.poppo.kotlin.basic.demo.exceptions.CustomerNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

internal class CustomerServiceTests {

    private lateinit var customerService: CustomerService

    @Mock
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    private fun setup() {
        MockitoAnnotations.initMocks(this)

        customerService = CustomerService(customerRepository)
    }

    @Test
    fun `Get all customers`() {

        given(customerRepository.findAll()).willReturn(arrayListOf(Customer(1, "testUser")))

        val customers = customerService.getCustomers()

        assertThat(customers[0].name).isEqualTo("testUser")
    }

    @Test
    fun `Get one exist customer by id`() {

        given(customerRepository.findCustomerById(1)).willReturn((Customer(1, "testUser")))

        val customer = customerService.getCustomer(1)

        assertThat(customer.name).isEqualTo("testUser")
    }

    @Test
    fun `Get one not exist customer by id`() {

        Assertions.assertThrows(CustomerNotFoundException::class.java) {
            customerService.getCustomer(4)
        }
    }

    @Test
    fun `Create one customer which is not existed`() {

        given(customerRepository.save(any(Customer::class.java))).willReturn(Customer(2, "testUser"))

        val createCustomer = customerService.createCustomer("testUser")

        assertThat(createCustomer.id).isEqualTo(2)
    }

    @Test
    fun `Delete one customer which is existed`() {

        given(customerRepository.findCustomerById(1)).willReturn((Customer(1, "testUser")))

        customerService.deleteCustomer(1)
        verify(customerRepository).deleteById(1)
    }

    @Test
    fun `Delete one customer which is not existed`() {

        Assertions.assertThrows(CustomerNotFoundException::class.java) {
            customerService.deleteCustomer(4)
        }
    }

    @Test
    fun `Update one customer which is existed`() {

        given(customerRepository.findCustomerById(1)).willReturn((Customer(1, "testUser")))

        customerService.updateCustomerName(1, "updated")

        verify(customerRepository).save(any(Customer::class.java))
    }

    @Test
    fun `Update one customer which is not existed`() {

        Assertions.assertThrows(CustomerNotFoundException::class.java) {
            customerService.updateCustomerName(4, "invalidTarget")
        }
    }
}
