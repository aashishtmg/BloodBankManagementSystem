package com.example.bloodbankmanagementsystem

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.bloodbankmanagementsystem.entity.User
import com.example.bloodbankmanagementsystem.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupActivity : AppCompatActivity() {
    private lateinit var edfname: EditText
    private lateinit var edlname: EditText
    private lateinit var edusername: EditText
    private lateinit var edaddress: EditText
    private lateinit var edemail: EditText
    private lateinit var edphone: EditText
    private lateinit var edpassword: EditText
    private lateinit var confirmpassword: EditText
    private lateinit var signup: Button
    private lateinit var asignup: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    var databaseReference: DatabaseReference? =null
    var database : FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val actionBar = supportActionBar
        actionBar!!.hide()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("USERS")

        edfname = findViewById(R.id.edfname)
        edlname = findViewById(R.id.edlname)
        edusername = findViewById(R.id.edusername)
        edaddress = findViewById(R.id.edaddress)
        edemail = findViewById(R.id.edemail)
        edphone = findViewById(R.id.edphone)
        edpassword = findViewById(R.id.edpassword)
        confirmpassword = findViewById(R.id.confirmpassword)
        signup = findViewById(R.id.btnsignup)
        asignup = findViewById(R.id.asighnup)
        db = FirebaseFirestore.getInstance()

        asignup.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        signup.setOnClickListener {
            val fname = edfname.text.toString()
            val lname = edlname.text.toString()
            val username = edusername.text.toString()
            val address = edaddress.text.toString()
            val email = edemail.text.toString()
            val phone = edphone.text.toString()
            val password = edpassword.text.toString()
            val confirmPassword = confirmpassword.text.toString()
            var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+"

            when {
                password != confirmPassword -> {
                    confirmpassword.error = "Incorrect Password"
                    confirmpassword.requestFocus()
                    return@setOnClickListener
                }
                username.isEmpty() -> {
                    edusername.error = "Enter username!!"
                    edusername.requestFocus()
                }
                email.trim() { it <= ' ' }.matches(emailPattern.toRegex()) -> {
                    edemail.error = "Enter valid Email!!"
                    edemail.requestFocus()
                }


                password.isEmpty() -> {
                    edpassword.error = "Enter password!!"
                    edpassword.requestFocus()
                }
                else -> {
                    auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener {
                            if (it.isSuccessful){
                                val currentUser = auth.currentUser
                                val currentUserdb = databaseReference?.child((currentUser?.uid!!))
                                currentUserdb?.child("fname")?.setValue(fname)
                                currentUserdb?.child("lname")?.setValue(lname)
                                currentUserdb?.child("username")?.setValue(username)
                                currentUserdb?.child("address")?.setValue(address)
                                currentUserdb?.child("email")?.setValue(email)
                                currentUserdb?.child("phone")?.setValue(phone)

                                Toast.makeText(this, "Registration success", Toast.LENGTH_SHORT).show()
                                finish()

                            } else {
                                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                            }
                        }

                    //sign in to api
                    val user = User(
                        fname = fname,
                        lname = lname,
                        username = username,
                        address = address,
                        email = email,
                        phone = phone,
                        password = password
                    )
                    CoroutineScope(Dispatchers.IO).launch {

                        try {
                            val userRepository = UserRepository()
                            val response = userRepository.registerUser(user)
                            if (response.success == true) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        this@SignupActivity,
                                        "User registered successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } catch (ex: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@SignupActivity,
                                    ex.message, Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}