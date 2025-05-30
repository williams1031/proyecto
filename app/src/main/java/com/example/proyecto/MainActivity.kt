package com.example.proyecto

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.widget.TextView
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.activity.enableEdgeToEdge

class MainActivity : AppCompatActivity() {

    private lateinit var laserLine: View
    private lateinit var logo: View
    private lateinit var text1: TextView
    private lateinit var text2: TextView
    private lateinit var text3: TextView
    private lateinit var text4: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        laserLine = findViewById(R.id.laserLine)
        logo = findViewById(R.id.imageView)
        text1 = findViewById(R.id.textView)
        text2 = findViewById(R.id.textView3)
        text3 = findViewById(R.id.textView4)
        text4 = findViewById(R.id.textView6)

        // Inicializar textos vacíos
        text1.text = ""
        text2.text = ""
        text3.text = ""
        text4.text = ""

        // Ejecutar escáner
        logo.post { startLaserScan() }
    }

    private fun startLaserScan() {
        val startY = logo.height.toFloat()
        val endY = 0f

        laserLine.visibility = View.VISIBLE
        laserLine.translationY = startY

        val animator = ObjectAnimator.ofFloat(
            laserLine,
            "translationY",
            startY,
            endY
        ).apply {
            duration = 1200
            interpolator = LinearInterpolator()
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = 1
        }

        animator.start()

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                laserLine.visibility = View.GONE
                startTypingSequence()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
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
        typeText(text1, getString(R.string.decifrar)) {
            typeText(text2, getString(R.string.protege)) {
                typeText(text3, getString(R.string.escapa)) {
                    typeText(text4, getString(R.string.texto1)) {

                        // Esperar 2 segundos antes de lanzar HomeActivity
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(this@MainActivity, homeactivity::class.java))
                            finish() // Finaliza MainActivity
                        }, 2000) // 2000 milisegundos = 2 segundos
                    }
                }
            }
        }
    }
}
