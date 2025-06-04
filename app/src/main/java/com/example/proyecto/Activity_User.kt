package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Activity_User : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        // Inicializa Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Referencias a los elementos del XML
        val inputNombre = findViewById<EditText>(R.id.inputNombre)
        val inputUsername = findViewById<EditText>(R.id.inputUsername)
        val inputEdad = findViewById<EditText>(R.id.inputEdad)
        val inputEmail = findViewById<EditText>(R.id.EmailUser)
        val inputRol = findViewById<EditText>(R.id.inputRol)
        val btnGuardarPerfil = findViewById<Button>(R.id.btnGuardarPerfil)

        // Obtener usuario actual
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "Error: Usuario no autenticado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Mostrar correo en el campo de email (no editable)
        inputEmail.setText(currentUser.email)
        inputEmail.isEnabled = false  // Deshabilita edición del campo

        // Guardar perfil al presionar el botón
        btnGuardarPerfil.setOnClickListener {
            val nombre = inputNombre.text.toString().trim()
            val username = inputUsername.text.toString().trim()
            val edad = inputEdad.text.toString().trim()
            val rol = inputRol.text.toString().trim()

            if (nombre.isEmpty() || username.isEmpty() || edad.isEmpty() || rol.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userId = currentUser.uid
            val userData = hashMapOf(
                "uid" to userId,
                "nombre" to nombre,
                "username" to username,
                "edad" to edad,
                "rol" to rol,
                "email" to currentUser.email
            )

            db.collection("usuarios").document(userId)
                .set(userData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Perfil guardado correctamente", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, alertaactivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al guardar perfil: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}
