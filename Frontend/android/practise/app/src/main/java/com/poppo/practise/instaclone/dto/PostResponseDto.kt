package com.poppo.practise.instaclone.dto

import java.io.Serializable

class PostResponseDto(
    var owner: String? = null,
    var content: String? = null,
    var image: String? = null
): Serializable