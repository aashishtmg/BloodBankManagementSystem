package com.example.bloodbankmanagementsystem

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    var currentProgess : Int = 1000
    private lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val actionBar = supportActionBar
        actionBar!!.hide()

        progressBar = findViewById(R.id.progressbar)

        progressBar.max = 1000

        ObjectAnimator.ofInt(progressBar, "progress", currentProgess)
            .setDuration(5000)
            .start()
        CoroutineScope(Dispatchers.IO).launch {
            delay(5000)
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)


            startActivity(intent)
            finish()
        }
    }
}