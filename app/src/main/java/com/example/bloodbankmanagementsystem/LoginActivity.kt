package com.example.bloodbankmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.bloodbankmanagementsystem.api.ServiceBuilder
import com.example.bloodbankmanagementsystem.repository.UserRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var register: TextView
    private lateinit var login: Button
    private lateinit var edusername: EditText
    private lateinit var edpassword: EditText
    private lateinit var linearLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val actionBar = supportActionBar
        actionBar!!.hide()

        edusername = findViewById(R.id.username)
        edpassword = findViewById(R.id.password)
        register = findViewById(R.id.register)
        login = findViewById(R.id.login)
        linearLayout = findViewById(R.id.linearlayout)

        login.setOnClickListener {
            loginfunction()
        }

        register.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginfunction() {

        val username = edusername.text.toString()
        val password = edpassword.text.toString()

        when {
            username.isEmpty() -> {
                edusername.error = "Enter username!!"
                edusername.requestFocus()
            }
            password.isEmpty() -> {
                edpassword.error = "Enter password!!"
                edpassword.requestFocus()
            }
            else -> {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val repository = UserRepository()
                        val response = repository.loginUser(username, password)
                        if (response.success == true) {
                            ServiceBuilder.token = "Bearer " + response.token

                            startActivity(
                                Intent(
                                    this@LoginActivity,
                                    MainActivity::class.java
                                )
                            )
                            finish()
                        } else {
                            withContext(Dispatchers.Main) {
                                val snack =
                                    Snackbar.make(
                                        linearLayout,
                                        "Invalid credentials",
                                        Snackbar.LENGTH_LONG
                                    )
                                snack.setAction("OK", View.OnClickListener {
                                    snack.dismiss()
                                })
                                snack.show()
                            }
                        }

                    } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            val snack =
                                Snackbar.make(
                                    linearLayout,
                                    "Login Error",
                                    Snackbar.LENGTH_LONG
                                )
                            snack.setAction("OK", View.OnClickListener {
                                snack.dismiss()
                            })
                            snack.show()
                        }
                    }
                }
            }
        }
    }
}