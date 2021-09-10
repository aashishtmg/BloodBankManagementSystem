package com.example.bloodbankmanagementsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.example.bloodbankmanagementsystem.entity.Donors
import com.example.bloodbankmanagementsystem.repository.DonorRepository
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class DonatebloodActivity : AppCompatActivity() {
    private val blood_group = arrayOf("A+","A-","B+","B-","O+","O-","AB+","AB-")
    private lateinit var edfullname: TextInputEditText
    private lateinit var edaddress: TextInputEditText
    private lateinit var edphone: TextInputEditText
    private lateinit var edbgroup: Spinner
    private lateinit var edage: TextInputEditText
    private lateinit var btnaddblood: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donateblood)

        edfullname=findViewById(R.id.edfullname)
        edaddress=findViewById(R.id.edaddress)
        edphone=findViewById(R.id.edphone)
        edbgroup=findViewById(R.id.edbgroup)
        edage=findViewById(R.id.edage)
        btnaddblood=findViewById(R.id.btnaddblood)

        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,blood_group)
        edbgroup.adapter=adapter

        btnaddblood.setOnClickListener {
            addDonor()
        }
    }

    private fun addDonor() {

        val fullname = edfullname.text.toString()
        val address = edaddress.text.toString()
        val phone = edphone.text.toString()
        val Bgroup = edbgroup.selectedItem.toString()
        val age=edage.text.toString()

        val donor = Donors(
            fullname = fullname,
            address = address,
            phone = phone,
            Bgroup = Bgroup,
            age=age
        )
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val donorRepository = DonorRepository()
                val response = donorRepository.addDonor(donor)

                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@DonatebloodActivity,
                            "Blood Information Added Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@DonatebloodActivity,
                        "Error ${ex.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

        }
    }
}