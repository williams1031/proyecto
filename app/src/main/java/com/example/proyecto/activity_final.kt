package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_final : AppCompatActivity() {

    private lateinit var textView6: TextView
    private lateinit var textView7: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_final)

        textView6 = findViewById(R.id.textView6)
        textView7 = findViewById(R.id.textView7)

        // Inicializar textos vacíos
        textView6.text = ""
        textView7.text = ""


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val RegisterText = findViewById<TextView>(R.id.ButtonRegister)
        RegisterText.setOnClickListener {
            val intent = Intent(this, actity_nivel1::class.java)
            startActivity(intent)
        }

        startTypingSequence()
    }

    private fun typeText(textView: TextView, fullText: String, onComplete: () -> Unit) {
        val handler = Handler(Looper.getMainLooper())
        var index = 0
        val delayMillis: Long = 40

        handler.postDelayed(object : Runnable {
            override fun run() {
                if (index <= fullText.length) {
                    textView.text = fullText.substring(0, index)
                    index++
                    handler.postDelayed(this, delayMillis)
                } else {
                    onComplete()
                }
            }
        }, delayMillis)
    }

    private fun startTypingSequence() {
        typeText(textView6, getString(R.string.felicitar)) {
            typeText(textView7, getString(R.string.txt_superado)) {
            }
        }
    }
}