package com.poppo.kotlin.basic.demo.exceptions

class CustomerNotFoundException(message: String): Exception(message)

class CustomerIdDuplicatedException(message: String): Exception(message)
