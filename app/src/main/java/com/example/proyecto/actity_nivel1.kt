package com.example.proyecto

import android.content.Intent
import android.os.CountDownTimer
import android.graphics.Color
import android.widget.TextView
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class actity_nivel1 : AppCompatActivity() {

    private lateinit var timerText: TextView
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var passwordInput: EditText
    private lateinit var checkUppercase: CheckBox
    private lateinit var checkLowercase: CheckBox
    private lateinit var checkNumber: CheckBox
    private lateinit var checkSymbol: CheckBox
    private lateinit var checkLength: CheckBox
    private lateinit var feedbackText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nivel1)

        timerText = findViewById(R.id.timerTextView)
        passwordInput = findViewById(R.id.passwordInput)
        checkUppercase = findViewById(R.id.checkUppercase)
        checkLowercase = findViewById(R.id.checkLowercase)
        checkNumber = findViewById(R.id.checkNumber)
        checkSymbol = findViewById(R.id.checkSymbol)
        checkLength = findViewById(R.id.checkLength)
        feedbackText = findViewById(R.id.feedbackText)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Deshabilitar los CheckBoxes al inicio
        disableCheckBoxes()

        passwordInput.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        startTimer()
    }

    private fun showMissionFailedDialog() {
        val dialogView = layoutInflater.inflate(R.layout.mision_fallida, null)
        val dialog = android.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        val retryButton = dialogView.findViewById<Button>(R.id.button_retry)
        val homeButton = dialogView.findViewById<Button>(R.id.button_home)

        retryButton.setOnClickListener {
            dialog.dismiss()
            recreate() // Reinicia esta activity
        }

        homeButton.setOnClickListener {
            dialog.dismiss()
            finish() // O navega a otra activity si es necesario
        }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(2 * 60 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                timerText.text = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                timerText.text = "00:00"
                passwordInput.isEnabled = false
                showMissionFailedDialog()
            }
        }.start()
    }

    private fun validatePassword(password: String) {
        val hasUppercase = password.any { it.isUpperCase() }
        val hasLowercase = password.any { it.isLowerCase() }
        val hasNumber = password.any { it.isDigit() }
        val hasSymbol = password.any { !it.isLetterOrDigit() }
        val hasLength = password.length >= 8

        checkUppercase.isChecked = hasUppercase
        checkLowercase.isChecked = hasLowercase
        checkNumber.isChecked = hasNumber
        checkSymbol.isChecked = hasSymbol
        checkLength.isChecked = hasLength

        // Cambiar el color del texto de los CheckBoxes
        checkUppercase.setTextColor(if (hasUppercase) Color.GREEN else Color.RED)
        checkLowercase.setTextColor(if (hasLowercase) Color.GREEN else Color.RED)
        checkNumber.setTextColor(if (hasNumber) Color.GREEN else Color.RED)
        checkSymbol.setTextColor(if (hasSymbol) Color.GREEN else Color.RED)
        checkLength.setTextColor(if (hasLength) Color.GREEN else Color.RED)

        // Mensaje de retroalimentación
        feedbackText.text = if (hasUppercase && hasLowercase && hasNumber && hasSymbol && hasLength) {
            enableCheckBoxes() // Habilitar CheckBoxes si se cumplen todos los requisitos
            showLevelCompletedDialog()
            "¡Contraseña segura!"

        } else {
            disableCheckBoxes() // Deshabilitar CheckBoxes si no se cumplen todos los requisitos
            "Asegúrate de cumplir todos los requisitos."
        }

        // Ajustar el margen superior del feedbackText
        val params = feedbackText.layoutParams as ConstraintLayout.LayoutParams
        params.topMargin = if (feedbackText.text.isNotEmpty()) 50 else 0 // Ajustar el margen según el contenido
        params.leftMargin = 50 // Ajustar el margen izquierdo
        feedbackText.layoutParams = params
    }

    private fun disableCheckBoxes() {
        checkUppercase.isEnabled = false
        checkLowercase.isEnabled = false
        checkNumber.isEnabled = false
        checkSymbol.isEnabled = false
        checkLength.isEnabled = false
    }

    private fun enableCheckBoxes() {
        checkUppercase.isEnabled = true
        checkLowercase.isEnabled = true
        checkNumber.isEnabled = true
        checkSymbol.isEnabled = true
        checkLength.isEnabled = true
    }

    private fun showLevelCompletedDialog() {
        val dialogView = layoutInflater.inflate(R.layout.mision_completada, null)
        val dialog = android.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        val progressBar = dialogView.findViewById<ProgressBar>(R.id.progressBar)
        val statusText = dialogView.findViewById<TextView>(R.id.statusText)
        val completeButton = dialogView.findViewById<Button>(R.id.buttonComplete)

        completeButton.isEnabled = false
        completeButton.alpha = 0.5f

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
                    statusText.text = "✔ Sistema validado exitosamente"

                    // Espera 2 segundos y lanza la siguiente activity
                    handler.postDelayed({
                        dialog.dismiss()
                        startActivity(Intent(this@actity_nivel1, activity_nivel2::class.java))
                        finish()
                    }, 5000)
                }
            }
        }, 40)
    }
}
