package com.example.bloodbankmanagementsystem

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.bloodbankmanagementsystem.api.ServiceBuilder
import com.example.bloodbankmanagementsystem.notification.NotificationsChannels
import com.example.bloodbankmanagementsystem.repository.UserRepository
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var register: TextView
    private lateinit var login: Button
    private lateinit var edusername: EditText
    private lateinit var edpassword: EditText
    private lateinit var linearLayout: LinearLayout
    private lateinit var checkbox: CheckBox
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth: FirebaseAuth
    var isRemembered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val actionBar = supportActionBar
        actionBar!!.hide()

        edusername = findViewById(R.id.email)
        edpassword = findViewById(R.id.password)
        register = findViewById(R.id.register)
        login = findViewById(R.id.login)
        checkbox = findViewById(R.id.rememberme)
        linearLayout = findViewById(R.id.linearlayout)

        auth = FirebaseAuth.getInstance()

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean("CHECKBOX", false)

        if (isRemembered) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

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
                edusername.error = "Enter email!!"
                edusername.requestFocus()
            }
            password.isEmpty() -> {
                edpassword.error = "Enter password!!"
                edpassword.requestFocus()
            }
            else -> {
                auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            var intent =Intent(this,MainActivity::class.java)
                            intent.putExtra("username",username)
                            startActivity(intent)
                            welcomenotifiy()
                            finish()
                        } else {
                            Toast.makeText(this, "Wrong Details", Toast.LENGTH_LONG).show()
                        }
                    }
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val repository = UserRepository()
                        val response = repository.loginUser(username, password)
                        if (response.success == true) {
                            ServiceBuilder.token = "Bearer " + response.token

                            savepref()
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

    private fun welcomenotifiy() {
        Handler().postDelayed({
            val notificationManager = NotificationManagerCompat.from(this)
            val notificationChannels = NotificationsChannels(this)
            notificationChannels.createNotificationChannels()

            val notification = NotificationCompat.Builder(this, notificationChannels.channel_1)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Blood Bank App")
                .setContentText("Welcome to Blood Bank app")
                .setColor(Color.BLUE)
                .build()

            notificationManager.notify(1, notification)

        }, 3000.toLong())
    }

    private fun savepref() {
        val checked: Boolean = checkbox.isChecked
        val edusername = edusername.text.toString()
        val edpassword = edpassword.text.toString()

        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("username", edusername)
        editor.putString("password", edpassword)
        editor.putBoolean("CHECKBOX", checked)
        editor.apply()
    }
}