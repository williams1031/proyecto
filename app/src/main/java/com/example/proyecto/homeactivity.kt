package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class homeactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        var AccederButton = findViewById<Button>(R.id.AccederButton)
        AccederButton.setOnClickListener {
            val intent = Intent(this, loginactivity::class.java)
            startActivity(intent)
        }
        var RegisterText = findViewById<TextView>(R.id.RegisterButton)
        RegisterText.setOnClickListener {
            val intent = Intent(this, registrateactivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
    }

}