package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        btn_sign_in_landing.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }

        btn_sign_up_landing.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }
}