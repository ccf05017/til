package com.poppo.kotlin.basic.demo.controllers

import com.poppo.kotlin.basic.demo.applications.CustomerService
import com.poppo.kotlin.basic.demo.domain.Customer
import com.poppo.kotlin.basic.demo.exceptions.CustomerIdDuplicatedException
import com.poppo.kotlin.basic.demo.exceptions.CustomerNotFoundException
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(CustomerController::class)
internal class CustomerControllerTests(@Autowired val mockMvc: MockMvc) {

    @MockBean
    lateinit var customerService: CustomerService

    @Test
    fun `Get all customers`() {

        given(customerService.getCustomers()).willReturn(arrayListOf(Customer(1, "testUser")))

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk)
                .andExpect(content().string(containsString("[")))
                .andExpect(content().string(containsString("id")))
                .andExpect(content().string(containsString("name")))
                .andExpect(content().string(containsString("testUser")))
    }

    @Test
    fun `Get one exist customer`() {

        given(customerService.getCustomer(1)).willReturn(Customer(1, "testUser"))

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk)
                .andExpect(content().string(containsString("1")))
                .andExpect(content().string(containsString("testUser")))
    }

    @Test
    fun `Get one not exist customer`() {

        // 이거 왜 answer로 처리해야 되는 걸까?
        given(customerService.getCustomer(4)).willAnswer { throw CustomerNotFoundException("Customer Not Found") }

        mockMvc.perform(get("/customers/4"))
                .andExpect(status().isNotFound)
                .andExpect(content().string(containsString("Customer Not Found")))
    }

    @Test
    fun `Create one customer which is not exist`() {

        given(customerService.createCustomer(1, "testUser"))
                .willReturn(Customer(1, "testUser"))

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"name\": \"testUser\"\n" +
                        "}"))
                .andExpect(status().isCreated)
                .andExpect(header().stringValues("Location", "/customers/1"))
    }

    @Test
    fun `Create one customer which is already exist`() {

        given(customerService.createCustomer(333, "exist"))
                .willAnswer { throw CustomerIdDuplicatedException("Customer Id Duplicated") }

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"id\": 333,\n" +
                        "  \"name\": \"exist\"\n" +
                        "}"))
                .andExpect(status().isBadRequest)
                .andExpect(content().string(containsString("Customer Id Duplicated")))
    }

    @Test
    fun `Delete one customer which is existed`() {

        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isOk)

        verify(customerService).deleteCustomer(1)
    }

    @Test
    fun `Delete one customer which is not existed`() {

        given(customerService.deleteCustomer(4))
                .willAnswer { throw CustomerNotFoundException("Customer Not Found") }

        mockMvc.perform(delete("/customers/4"))
                .andExpect(status().isNotFound)
                .andExpect(content().string(containsString("Customer Not Found")))
    }

    @Test
    fun `Update one customer which is existed`() {

        mockMvc.perform(patch("/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"id\": 333,\n" +
                        "  \"name\": \"updated\"\n" +
                        "}"))
                .andExpect(status().isOk)

        verify(customerService).updateCustomer(1, 333, "updated")
    }

    @Test
    fun `Update one customer which is not existed`() {

        given(customerService.updateCustomer(4, 333, "updated"))
                .willAnswer { throw CustomerNotFoundException("Customer Not Found") }

        mockMvc.perform(patch("/customers/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"id\": 333,\n" +
                        "  \"name\": \"updated\"\n" +
                        "}"))
                .andExpect(status().isNotFound)
                .andExpect(content().string(containsString("Customer Not Found")))
    }
}