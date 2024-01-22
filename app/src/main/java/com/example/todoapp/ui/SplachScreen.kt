package com.example.todoapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.todoapp.R

class SplachScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach_screen)
         Handler(Looper.getMainLooper()).postDelayed({
             startMain()
         },2000)
    }
    fun startMain(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}