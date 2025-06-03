package com.example.proyecto

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class loginactivity : AppCompatActivity() {

    private var isPasswordVisible = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val inputPassword = findViewById<EditText>(R.id.editTextText2)
        val buttonTogglePassword = findViewById<ImageButton>(R.id.icon3)
        val linkGoHome = findViewById<ImageButton>(R.id.btn_atras)

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

        // Ir al home
        linkGoHome.setOnClickListener {
            val intent = Intent(this, homeactivity::class.java)
            startActivity(intent)
        }

        // Toggle para mostrar/ocultar contraseña
        buttonTogglePassword.setOnClickListener {
            if (isPasswordVisible) {
                // Ocultar contraseña
                inputPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                        android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                buttonTogglePassword.setImageResource(R.drawable.icono_no_ver_contrase_a) // Cambia al icono de ocultar
            } else {
                // Mostrar contraseña
                inputPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                        android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                buttonTogglePassword.setImageResource(R.drawable.icono_ver_contrase_a) // Cambia al icono de mostrar
            }
            // Mantener el cursor al final
            inputPassword.setSelection(inputPassword.text.length)
            isPasswordVisible = !isPasswordVisible
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}