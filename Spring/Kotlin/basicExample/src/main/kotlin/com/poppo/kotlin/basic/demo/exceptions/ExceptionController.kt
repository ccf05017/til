package com.poppo.kotlin.basic.demo.exceptions

import com.poppo.kotlin.basic.demo.dtos.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ExceptionController {

    @ExceptionHandler(CustomerNotFoundException::class)
    fun customerNotFoundExceptionHandler(servletRequest: HttpServletRequest,
                                         exception: Exception)
    : ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse("Customer Not Found", exception.message!!), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(CustomerIdDuplicatedException::class)
    fun customerIdDuplicatedExceptionHandler(servletRequest: HttpServletRequest,
                                         exception: Exception)
            : ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse("Customer Id Duplicated", exception.message!!), HttpStatus.BAD_REQUEST)
    }
}