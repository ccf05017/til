package com.poppo.practise

import com.poppo.practise.instaclone.dto.LoginResponseDto
import com.poppo.practise.instaclone.dto.PostResponseDto
import com.poppo.practise.instaclone.dto.RegisterRequestDto
import com.poppo.practise.instaclone.dto.RegisterResponseDto
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @GET("json/students/")
    fun getStudentsList(): Call<ArrayList<PersonFromServer>>

    @POST("json/students/")
    fun createStudent(
        @Body params: HashMap<String, Any>
    ): Call<PersonFromServer>

    @POST("json/students/")
    fun createStudentEasy(
        @Body person: PersonFromServer
    ): Call<PersonFromServer>

    @POST("user/signup/")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String,
        @Field("password1") password1: String,
        @Field("password2") password2: String
    ): Call<RegisterResponseDto>

    @POST("user/login/")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponseDto>

    @GET("instagram/post/list/all")
    fun getAllPosts(): Call<ArrayList<PostResponseDto>>
}