package com.example.bloodbankmanagementsystem.api

import com.example.bloodbankmanagementsystem.entity.Donors
import com.example.bloodbankmanagementsystem.response.AddDonorResponse
import com.example.bloodbankmanagementsystem.response.DeleteDonorResponse
import com.example.bloodbankmanagementsystem.response.GetAllDonorResponse
import com.example.bloodbankmanagementsystem.response.UpdateResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface DonorAPI {

    //Add Donor
    @POST("bgroup/insert")
    suspend fun addDonor(
        @Header("Authorization") token:String,
        @Body donor: Donors
    ): Response<AddDonorResponse>

    //Delete Donor
    @DELETE("bgroup/delete/{id}")
    suspend fun deleteDonor(
        @Header("Authorization") token: String,
        @Path("id")id:String
    ): Response<DeleteDonorResponse>

    //GET all student
    @GET("bgroup/all")
    suspend fun getAllDonor(
        @Header("Authorization") token : String,
    ): Response<GetAllDonorResponse>

    @PUT("bgroup/update/{id}")
    suspend fun updateDonor(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body donor: Donors
    ): Response<UpdateResponse>

}