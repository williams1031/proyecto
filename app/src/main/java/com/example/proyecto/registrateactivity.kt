package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class registrateactivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrate)

        auth = FirebaseAuth.getInstance() // Inicializa FirebaseAuth

        val inputEmail = findViewById<EditText>(R.id.InputEmail)
        val inputPassword = findViewById<EditText>(R.id.InputPassword)
        val inputConfirmPassword = findViewById<EditText>(R.id.InputConfirmPassword)
        val buttonSubmit = findViewById<Button>(R.id.ButtonRegister)
        val linkGoLogin = findViewById<TextView>(R.id.text_iniciar_sesion)
        val linkGoHome = findViewById<TextView>(R.id.btn_atras)
        val buttonTogglePassword = findViewById<ImageButton>(R.id.icon5)
        val buttonToggleConfirmPassword = findViewById<ImageButton>(R.id.icon7)


        buttonSubmit.setOnClickListener {
            val email = inputEmail.text.toString().trim()
            val password = inputPassword.text.toString().trim()
            val confirmPassword = inputConfirmPassword.text.toString().trim()

            // Validaciones
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear cuenta con Firebase
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        // Opcional: ir al login o a la pantalla principal
                        val intent = Intent(this, alertaactivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val error = task.exception?.message ?: "Error desconocido"
                        Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
                    }
                }
        }

        // Ir al login
        linkGoLogin.setOnClickListener {
            val intent = Intent(this, loginactivity::class.java)
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

        // Toggle para mostrar/ocultar contraseña
        buttonToggleConfirmPassword.setOnClickListener {
            if (isPasswordVisible) {
                // Ocultar contraseña
                inputConfirmPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                        android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                buttonToggleConfirmPassword.setImageResource(R.drawable.icono_no_ver_contrase_a) // Cambia al icono de ocultar
            } else {
                // Mostrar contraseña
                inputConfirmPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                        android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                buttonToggleConfirmPassword.setImageResource(R.drawable.icono_ver_contrase_a) // Cambia al icono de mostrar
            }
            // Mantener el cursor al final
            inputConfirmPassword.setSelection(inputConfirmPassword.text.length)
            isPasswordVisible = !isPasswordVisible
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
