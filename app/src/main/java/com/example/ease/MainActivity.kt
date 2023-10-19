package com.example.ease

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initEvent()
    }
    fun initEvent(){
        val button: Button=findViewById<Button>(R.id.button)
        button.setOnClickListener(){
            val intent = Intent(this,MisAutosPublicados::class.java)
            startActivity(intent)
        }
    }
}