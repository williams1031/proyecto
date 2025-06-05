package com.example.proyecto

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TerminalUserInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val tvNombre: TextView
    private val tvRol: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.component_terminal_info, this, true)
        tvNombre = findViewById(R.id.tvNombre)
        tvRol = findViewById(R.id.tvRol)
        cargarDatosUsuario()
    }

    private fun cargarDatosUsuario() {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val user = auth.currentUser

        if (user != null) {
            val uid = user.uid
            db.collection("usuarios").document(uid).get()
                .addOnSuccessListener { doc ->
                    val nombre = doc.getString("nombre") ?: "Desconocido"
                    val rol = doc.getString("rol") ?: "Desconocido"
                    tvNombre.text = "> Usuario conectado: $nombre"
                    tvRol.text = "> Rol: $rol"
                }
                .addOnFailureListener {
                    tvNombre.text = "> Error al cargar usuario"
                    tvRol.text = "> "
                }
        } else {
            tvNombre.text = "> No hay usuario autenticado"
            tvRol.text = "> "
        }
    }
}
