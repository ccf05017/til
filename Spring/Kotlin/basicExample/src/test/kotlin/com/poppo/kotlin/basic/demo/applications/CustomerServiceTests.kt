package com.poppo.kotlin.basic.demo.applications

import com.poppo.kotlin.basic.demo.domain.Customer
import com.poppo.kotlin.basic.demo.domain.CustomerRepository
import com.poppo.kotlin.basic.demo.exceptions.CustomerNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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

        given(customerRepository.getOne(1)).willReturn((Customer(1, "testUser")))

        val customer = customerService.getCustomer(1)

        assertThat(customer.name).isEqualTo("testUser")
    }

    @Test
    fun `Get one not exist customer by id`() {

        given(customerRepository.getOne(4)).willAnswer { throw CustomerNotFoundException("") }

        val exception = assertThrows<CustomerNotFoundException> {
            customerService.getCustomer(4)
        }
        assertEquals("Customer Not Found", exception.message)
    }

    @Test
    fun `Create one customer which is not existed`() {

        given(customerRepository.save(any(Customer::class.java))).willReturn(Customer(2, "testUser"))

        val createCustomer = customerService.createCustomer("testUser")

        assertThat(createCustomer.id).isEqualTo(2)
    }

    @Test
    fun `Delete one customer which is existed`() {

        given(customerRepository.getOne(1)).willReturn((Customer(1, "testUser")))

        customerService.deleteCustomer(1)
        verify(customerRepository).deleteById(1)
    }

    @Test
    fun `Delete one customer which is not existed`() {

        given(customerRepository.getOne(4)).willAnswer { throw CustomerNotFoundException("") }

        val exception = assertThrows<CustomerNotFoundException> {
            customerService.getCustomer(4)
        }
        assertEquals("Customer Not Found", exception.message)
    }

    @Test
    fun `Update one customer which is existed`() {

        given(customerRepository.getOne(1)).willReturn(Customer(1, "testUser"))

        customerService.changeName(1, "updated")

        verify(customerRepository).save(any(Customer::class.java))
    }

    @Test
    fun `Update one customer which is not existed`() {

        given(customerRepository.getOne(4)).willAnswer { throw CustomerNotFoundException("") }

        val exception = assertThrows<CustomerNotFoundException> {
            customerService.getCustomer(4)
        }
        assertEquals("Customer Not Found", exception.message)
    }
}
