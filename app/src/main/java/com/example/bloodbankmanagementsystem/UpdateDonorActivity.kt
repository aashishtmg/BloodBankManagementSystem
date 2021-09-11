package com.example.bloodbankmanagementsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.bloodbankmanagementsystem.entity.Donors
import com.example.bloodbankmanagementsystem.repository.DonorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateDonorActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var address: EditText
    private lateinit var phone: EditText
    private lateinit var bgroup: EditText
    private lateinit var age: EditText
    private lateinit var btnupdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_donor)

        name=findViewById(R.id.name)
        address=findViewById(R.id.address)
        phone=findViewById(R.id.phone)
        bgroup=findViewById(R.id.bgroup)
        age=findViewById(R.id.age)
        btnupdate=findViewById(R.id.btnUpdate)

        val intent = intent.getParcelableExtra<Donors>("Donor")
        if (intent !=null){
            name.setText(intent.fullname)
            address.setText(intent.address)
            phone.setText(intent.phone)
            bgroup.setText(intent.Bgroup)
            age.setText(intent.age)
        }


        btnupdate.setOnClickListener {
            updateapi()
        }
    }

    private fun updateapi() {

        val intent = intent.getParcelableExtra<Donors>("Donor")
        val fullname = name.text.toString()
        val address = address.text.toString()
        val phone = phone.text.toString()
        val bgroup = bgroup.text.toString()
        val age = age.text.toString()

        val donor = Donors(fullname = fullname,address = address,
            phone = phone,Bgroup = bgroup,age = age)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val donorreposiTory = DonorRepository()
                val response = donorreposiTory.updateDonor(intent?._id!!,donor)
                if (response.message != null){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@UpdateDonorActivity, "updated successfully", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (ex:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@UpdateDonorActivity, ex.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}