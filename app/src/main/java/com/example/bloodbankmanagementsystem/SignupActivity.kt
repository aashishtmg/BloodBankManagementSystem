package com.example.bloodbankmanagementsystem

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val actionBar = supportActionBar
        actionBar!!.hide()

        auth = FirebaseAuth.getInstance()

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
        db= FirebaseFirestore.getInstance()

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

                    val users = hashMapOf(
                        "fname" to fname,
                        "lname" to lname,
                        "username" to username,
                        "address" to address,
                        "email" to email,
                        "phone" to phone
                    )
                    val Users = db.collection("USERS")
                    val query = Users.whereEqualTo("email", email).get()
                        .addOnSuccessListener { tasks ->
                            if (tasks.isEmpty) {
                                auth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(this) { task ->
                                        if (task.isSuccessful) {
                                            Users.document(email).set(users)

                                            val intent =
                                                Intent(this, LoginActivity::class.java)
                                            intent.putExtra("email", email)

                                            startActivity(intent)
                                            finish()
                                        } else {
                                            Toast.makeText(
                                                this,
                                                "Authentication Failed",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                            } else {
                                Toast.makeText(this, "User Already Registered", Toast.LENGTH_LONG)
                                    .show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    signintoapi()
                }
            }
        }
    }

    private fun signintoapi() {
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

                val user = User(
                    fname = fname,
                    lname = lname,
                    username = username,
                    address = address,
                    email = email,
                    phone = phone,
                    password = password
                )

                val intent = Intent(this, LoginActivity::class.java)
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
                                //  startActivity(intent)
                            }
                            // Switch to Main thread
                            startActivity(intent)

                        }
                    } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@SignupActivity,
                                "Registration failed!!", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}