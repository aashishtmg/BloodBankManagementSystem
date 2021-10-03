package com.example.bloodbankmanagementsystem

import com.example.bloodbankmanagementsystem.api.ServiceBuilder
import com.example.bloodbankmanagementsystem.entity.Donors
import com.example.bloodbankmanagementsystem.entity.User
import com.example.bloodbankmanagementsystem.repository.DonorRepository
import com.example.bloodbankmanagementsystem.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class LoginTest {
    private lateinit var userRepository: UserRepository
    private lateinit var donorRepository: DonorRepository

    //=============User Testing==================
    @Test
    fun checklogin() = runBlocking {
        userRepository = UserRepository()
        val response = userRepository.loginUser("aashish@gmail.com","aashish")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult,actualResult)
    }

    @Test
    fun registerUser() = runBlocking {
        val user =
            User(fname = "kali",lname = "prasad",
                username = "kali",address = "kathmandu",
                email = "kali@gmail.com",phone = "984837282",password = "kali")
        userRepository = UserRepository()
        val response = userRepository.registerUser(user)
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    //=============Donor Add====================
    @Test
    fun donoradd() = runBlocking {
        donorRepository = DonorRepository()
        userRepository = UserRepository()

        val donor = Donors(fullname = "kailash",address = "kalimati",phone = "985345",Bgroup = "B+",age = "23")

        ServiceBuilder.token ="Bearer " + userRepository.loginUser("aashish@gmail.com","aashish").token

        val ExpectedResult = true
        val ActualResult = donorRepository.addDonor(donor).success
        Assert.assertEquals(ExpectedResult,ActualResult)
    }
}