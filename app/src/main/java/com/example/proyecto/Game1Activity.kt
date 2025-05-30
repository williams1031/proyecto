package com.example.proyecto
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class Game1Activity : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var questionNumberText: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var nextButton: Button

    private var questions: List<Map<String, Any>> = listOf()
    private var currentIndex = 0
    private var score = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game1)

        // Referencias a elementos de la UI
        questionText = findViewById(R.id.questionText)
        questionNumberText = findViewById(R.id.questionNumberText)
        optionsGroup = findViewById(R.id.optionsGroup)
        nextButton = findViewById(R.id.nextButton)

        // Cargar preguntas desde Firestore

        val db = Firebase.firestore
        db.collection("preguntas")
            .get()
            .addOnSuccessListener { result ->
                questions = result.documents.mapNotNull { it.data }
                showQuestion()
            }

//        FirebaseFirestore.getInstance().collection("questions")
//            .get()
//            .addOnSuccessListener { result ->
//                questions = result.documents.map { it.data!! }
//                showQuestion()
//            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar preguntas", Toast.LENGTH_SHORT).show()
            }

        // Siguiente pregunta al hacer click
        nextButton.setOnClickListener {
            checkAnswer()
        }
    }

    private fun showQuestion() {
        if (currentIndex >= questions.size) {
            showResult()
            return
        }

        val question = questions[currentIndex]
        val title = question["question"] as String
        val options = question["options"] as List<*>

        questionNumberText.text = "Pregunta ${currentIndex + 1}"
        questionText.text = title

        optionsGroup.removeAllViews()
        options.forEach { option ->
            val radioButton = RadioButton(this)
            radioButton.text = option.toString()
            radioButton.textSize = 18f
            optionsGroup.addView(radioButton)
        }
    }

    private fun checkAnswer() {
        val selectedId = optionsGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(this, "Selecciona una opción", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedOption = findViewById<RadioButton>(selectedId).text.toString()
        val correctAnswer = questions[currentIndex]["answer"] as String

        if (selectedOption == correctAnswer) score++

        currentIndex++
        showQuestion()
    }

    private fun showResult() {
        AlertDialog.Builder(this)
            .setTitle("Juego terminado")
            .setMessage("Tu puntaje: $score/${questions.size}")
            .setPositiveButton("OK") { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }
}
