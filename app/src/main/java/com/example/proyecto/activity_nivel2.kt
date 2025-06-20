package com.example.proyecto

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.color.utilities.Score



class activity_nivel2 : AppCompatActivity() {

    private lateinit var timerTextView: TextView
    private var countDownTimer: CountDownTimer? = null
    private val totalTime: Long = 120000 // 2 minutos en milisegundos
    private val interval: Long = 1000 // 1 segundo en milisegundos
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nivel2)

        timerTextView = findViewById(R.id.timerTextView)

        setupHotspots()
        startTimer()

    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(totalTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                // Calcula los minutos y segundos restantes
                val seconds = (millisUntilFinished / 1000) % 60
                val minutes = (millisUntilFinished / (1000 * 60))
                // Muestra el tiempo en el TextView
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }
            override fun onFinish() {
                timerTextView.text = "00:00"
                // Puedes agregar cualquier acción cuando termine el temporizador
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel() // Cancelar el temporizador si la actividad se destruye
    }

    private fun setupHotspots() {
        val hotspotSender = findViewById<View>(R.id.hotspotSender)
        val hotspotSubject = findViewById<View>(R.id.hotspotSubject)
        val hotspotButton = findViewById<View>(R.id.hotspotButtom)
        val hotspotLink = findViewById<View>(R.id.hotspotLink)

        val context = this

        val touchedHotspots = mutableSetOf<Int>() // Para registrar los hotspots tocados

        fun handleHotspotClick(id: Int, message: String) {
            if (!touchedHotspots.contains(id)) {
                touchedHotspots.add(id)
                validateHotspot(true)
            } else {
                Toast.makeText(context, "Este ya fue detectado.", Toast.LENGTH_SHORT).show()
            }

            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

            if (touchedHotspots.size == 4) {
                showLevel2CompletedDialog()
            }
        }

        hotspotSender.setOnClickListener {
            handleHotspotClick(R.id.hotspotSender, "📧 Remitente sospechoso detectado")
        }

        hotspotSubject.setOnClickListener {
            handleHotspotClick(R.id.hotspotSubject, "✉️ Asunto sospechoso detectado")
        }

        hotspotButton.setOnClickListener {
            handleHotspotClick(R.id.hotspotButtom, "⚠️ Botón sospechoso detectado")
        }

        hotspotLink.setOnClickListener {
            handleHotspotClick(R.id.hotspotLink, "🔗 Link sospechoso detectado")
        }
    }

    private fun validateHotspot(isCorrect: Boolean) {
        if (isCorrect) {
            score++
            Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Intenta de nuevo.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLevel2CompletedDialog() {
        val dialogView = layoutInflater.inflate(R.layout.mision_completada2, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        val progressBar = dialogView.findViewById<ProgressBar>(R.id.progressBar)
        val statusText = dialogView.findViewById<TextView>(R.id.statusText)
        val completeButton = dialogView.findViewById<Button>(R.id.buttonComplete)

        // Animar el progreso con Handler
        val handler = Handler(Looper.getMainLooper())
        var progress = 0

        handler.postDelayed(object : Runnable {
            override fun run() {
                if (progress < 100) {
                    progress += 4
                    progressBar.progress = progress
                    handler.postDelayed(this, 40)
                } else {
                    // Una vez completado
                    completeButton.text = "✔ NIVEL COMPLETADO"
                    completeButton.isEnabled = true
                    completeButton.alpha = 1.0f
                    statusText.text = "✔ Todas las amenazas fueron detectadas con éxito"

                    // Espera 2 segundos y lanza la siguiente activity
                    handler.postDelayed({
                        dialog.dismiss()
                        startActivity(Intent(this@activity_nivel2, activity_final::class.java))
                        finish()
                    }, 5000)
                }
            }
        }, 40)
    }
}
