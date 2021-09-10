package com.example.bloodbankmanagementsystem.response

import com.example.bloodbankmanagementsystem.entity.Donors

class GetAllDonorResponse (
    val success:Boolean?=null,
    val count: Int?= null,
    val data: MutableList<Donors>?=null
)