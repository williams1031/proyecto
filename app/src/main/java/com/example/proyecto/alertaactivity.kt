package com.example.proyecto

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class alertaactivity : AppCompatActivity() {

    private lateinit var titleText2: TextView
    private lateinit var textView2: TextView
    private lateinit var subtitleText3: TextView
    private lateinit var buttonSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alerta)

        titleText2 = findViewById(R.id.titleText2)
        textView2 = findViewById(R.id.textView2)
        subtitleText3 = findViewById(R.id.subtitleText3)
        buttonSubmit = findViewById(R.id.loginButton2)

        // Inicializar textos vacíos
        textView2.text = ""
        subtitleText3.text = ""

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonSubmit = findViewById<Button>(R.id.loginButton2)
        buttonSubmit.visibility = View.INVISIBLE
        buttonSubmit.setOnClickListener {
            val intent = Intent(this, actity_nivel1::class.java)
            startActivity(intent)
            finish()
        }

        // Inicia animaciones
        startBlinkAnimation()
        startTypingSequence()
    }

    private fun startBlinkAnimation() {
        val animator = ObjectAnimator.ofFloat(titleText2, "alpha", 1f, 0f)
        animator.duration = 700
        animator.repeatMode = ValueAnimator.REVERSE
        animator.repeatCount = ValueAnimator.INFINITE
        animator.start()
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
        typeText(textView2, getString(R.string.texto4)) {
            typeText(subtitleText3, getString(R.string.texto5)) {
                // Una vez que se completa la escritura, animar el botón
                val buttonSubmit = findViewById<Button>(R.id.loginButton2)
                animateButton(buttonSubmit)
            }
        }
    }

    private fun animateButton(button: Button) {
        button.alpha = 0f
        button.translationY = 50f
        button.visibility = View.VISIBLE

        button.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(600)
            .start()
    }
}
