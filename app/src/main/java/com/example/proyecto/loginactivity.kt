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
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast

class loginactivity : AppCompatActivity() {

    private var isPasswordVisible = false
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        // Se inicia el proceso de "auth" de Firebase
        auth = FirebaseAuth.getInstance()
        // Variables para el manejo de los inputs
        val InputEmail = findViewById<EditText>(R.id.editTextText)
        val inputPassword = findViewById<EditText>(R.id.editTextText2)
        val buttonTogglePassword = findViewById<ImageButton>(R.id.icon3)

        // Button que redirecciona al usuario a la pantalla de Home
        val linkGoHome = findViewById<Button>(R.id.btn_atras)

        // Button Submit de los datos
        val LinkGoAcceder = findViewById<Button>(R.id.loginButton)

        // Link que redirije al usuario a la pantalla de RegisterActivity
        var LinkGoRegister = findViewById<TextView>(R.id.registerText)
        LinkGoRegister.setOnClickListener {
            val intent = Intent(this, registrateactivity::class.java)
            startActivity(intent)
        }

        LinkGoAcceder.setOnClickListener {
            val email = InputEmail.text.toString().trim()
            val password = inputPassword.text.toString().trim()

            // Valida si hay campos vacios
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //Metodo de firebase para hacer el SignIn
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Se ha iniciado tu sesion con exito", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, alertaactivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val error = task.exception?.message ?: "Error desconocido"
                        Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
                    }
                }

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