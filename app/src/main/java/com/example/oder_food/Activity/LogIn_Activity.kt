package com.example.oder_food.Activity;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.oder_food.R

class LogIn_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Handler().postDelayed({
            val mainIntent = Intent(this, Home_Activity::class.java)
            startActivity(mainIntent)
            finish()
        }, 2500)
    }
}


