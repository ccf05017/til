package com.poppo.practise.instaclone.dto

import java.io.Serializable

class RegisterRequestDto(
    var username: String? = null,
    var password1: String? = null,
    var password2: String? = null
): Serializable