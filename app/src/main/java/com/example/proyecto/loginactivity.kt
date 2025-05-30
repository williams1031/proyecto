package com.example.proyecto

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class loginactivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        // Link que redirije al usuario a la pantalla de RegisterActivity
        var LinkGoRegister = findViewById<TextView>(R.id.registerText)
        LinkGoRegister.setOnClickListener {
            val intent = Intent(this, registrateactivity::class.java)
            startActivity(intent)
        }
       val LinkGoAcceder = findViewById<Button>(R.id.loginButton)  // ACCEDE SIN VALIDAR PARA PRUEBAS
        LinkGoAcceder.setOnClickListener {
            val intent = Intent(this, alertaactivity::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}