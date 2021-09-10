package com.example.bloodbankmanagementsystem.repository

import com.example.bloodbankmanagementsystem.api.DonorAPI
import com.example.bloodbankmanagementsystem.api.MyApiRequest
import com.example.bloodbankmanagementsystem.api.ServiceBuilder
import com.example.bloodbankmanagementsystem.entity.Donors
import com.example.bloodbankmanagementsystem.response.AddDonorResponse
import com.example.bloodbankmanagementsystem.response.DeleteDonorResponse
import com.example.bloodbankmanagementsystem.response.GetAllDonorResponse
import com.example.bloodbankmanagementsystem.response.UpdateResponse

class DonorRepository: MyApiRequest() {

    private val DonorAPI =
        ServiceBuilder.buildService(DonorAPI::class.java)

    //Add Donor
    suspend fun addDonor(donor: Donors): AddDonorResponse {
        return apiRequest {
            DonorAPI.addDonor(
                ServiceBuilder.token!!,donor
            )
        }
    }

    //update donor
    suspend fun updateDonor(id: String,donor: Donors): UpdateResponse {
        return apiRequest {
            DonorAPI.updateDonor(
                ServiceBuilder.token!!,id,donor
            )
        }
    }

    //delete donor
    suspend fun deleteDonor(id: String): DeleteDonorResponse {
        return apiRequest {
            DonorAPI.deleteDonor(
                ServiceBuilder.token!!,id
            )
        }
    }


    //get Donor
    suspend fun getAllDonor(): GetAllDonorResponse {
        return apiRequest {
            DonorAPI.getAllDonor(
                ServiceBuilder.token!!
            )
        }
    }
}