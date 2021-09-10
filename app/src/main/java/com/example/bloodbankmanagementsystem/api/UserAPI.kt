package com.example.bloodbankmanagementsystem.api

import com.example.bloodbankmanagementsystem.entity.User
import com.example.bloodbankmanagementsystem.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserAPI {
    //register user
    @POST("bloodbank/client/register")
    suspend fun registerUser(
        @Body user: User
    ): Response<LoginResponse>

    //login user
    @FormUrlEncoded
    @POST("bloodbank/client/login")
    suspend fun checkUser(
        @Field("username") username:String,
        @Field("password") password:String
    ): Response<LoginResponse>
}