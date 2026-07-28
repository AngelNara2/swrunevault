package com.example.swrunevault.controls

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class StrokeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int =0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    var strokeColor = Color.BLACK
        set(value) {
            field = value
            invalidate()
        }
    var strokeWidth = 5f

    override fun onDraw(canvas: Canvas) {

        val fillColor = currentTextColor

        // Contorno
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        setTextColor(strokeColor)
        super.onDraw(canvas)

        // Relleno
        paint.style = Paint.Style.FILL
        setTextColor(fillColor)
        super.onDraw(canvas)
    }
}