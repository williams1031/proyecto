package com.example.proyecto

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class MatrixRainView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    //Color para las letras
    private val textPaint = Paint().apply {
        color = context.getColor(R.color.verde_oscuro)
        style = Paint.Style.FILL
        textSize = 40f
        isAntiAlias = true
    }

    private var widthColumns = 0 //Número de columnas
    private var height = 0

    private var columnPositions : IntArray = intArrayOf() // Para la posición de cada columna

    //Letras y simbolos
    private val characters = ("0123456789ABCDEFGHIJKLMNÑOPQRSTUVWXYZ" + "abcdefghijklmnñopqrstuvwxyz@#$%&*+-/").toCharArray()

    //Velocidad de caída para las columnas en pixeles
    private lateinit var columnSpeed: IntArray

    //Delay de refresco en ms
    private val frameDelay: Long = 50

    private val random = Random(System.currentTimeMillis())

    private val runnable = object : Runnable {
        override fun run() {
            updatePositions()
            invalidate()
            postDelayed(this, frameDelay)
        }
    }

    init {
        post (runnable) //Inicia el loop de animación
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int){
        super.onSizeChanged(w, h, oldw, oldh)
     height = h
     widthColumns = (w / textPaint.textSize).toInt()

    columnPositions = IntArray(widthColumns) { random.nextInt(-1000, 0) }
    columnSpeed = IntArray(widthColumns) { random.nextInt(10, 30) }
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //Fondo negro para el efecto
        canvas.drawColor(Color.argb(150,0,0,0))

        for (i in 0 until widthColumns) {
            val x = i * textPaint.textSize
            var y = columnPositions[i].toFloat()

            //Letras cayendo en cada columna
            while (y < height) {
                val randomChar = characters [random.nextInt(characters.size)].toString()
                canvas.drawText(randomChar, x, y, textPaint)
                y += textPaint.textSize
            }
        }
    }

    private fun updatePositions() {
        for (i in columnPositions.indices) {
            columnPositions[i] += columnSpeed[i]
            if (columnPositions[i] > height) {
                columnPositions[i] = random.nextInt(-1000, 0)
                columnSpeed[i] = random.nextInt(3, 10)
            }
        }
    }

    override fun onDetachedFromWindow() {
        removeCallbacks(runnable)
        super.onDetachedFromWindow()
    }
}