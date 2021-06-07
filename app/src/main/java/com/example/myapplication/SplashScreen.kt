package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.activity.HomeActivity
import com.example.myapplication.activity.LandingActivity
import com.example.myapplication.activity.SignIn

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // 4 second splash time
        Handler().postDelayed({
            //start main activity
            startActivity(Intent(this@SplashScreen, LandingActivity::class.java))
            //finish this activity
            finish()
        },5000)
    }
}