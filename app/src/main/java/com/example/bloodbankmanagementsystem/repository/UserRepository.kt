package com.example.bloodbankmanagementsystem.repository

import com.example.bloodbankmanagementsystem.api.MyApiRequest
import com.example.bloodbankmanagementsystem.api.ServiceBuilder
import com.example.bloodbankmanagementsystem.api.UserAPI
import com.example.bloodbankmanagementsystem.entity.User
import com.example.bloodbankmanagementsystem.response.LoginResponse

class UserRepository:
    MyApiRequest() {
    private val userAPI = ServiceBuilder.buildService(UserAPI::class.java)

    //Register User
    suspend fun registerUser(user: User) : LoginResponse {
        return apiRequest {
            userAPI.registerUser(user)
        }
    }

    //login User
    suspend fun loginUser(username:String, password:String):LoginResponse{
        return apiRequest {
            userAPI.checkUser(username, password)
        }
    }
}