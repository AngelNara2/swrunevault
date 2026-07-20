package com.example.swrunevault.controls

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class StatView(context: Context) : LinearLayout(context) {

    private val headerTextView: TextView
    private val iconView: ImageView
    private val secondTextView: TextView

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    init {
        orientation = VERTICAL

        layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT,
            1f
        )

        headerTextView = TextView(context).apply {
            textSize = 14f
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, 0, 0, dp(8))
        }

        addView(headerTextView)

        val dataLayout = LinearLayout(context).apply {
            orientation = HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        iconView = ImageView(context).apply {
            layoutParams = LayoutParams(50, 50)
            scaleType = ImageView.ScaleType.FIT_CENTER
        }

        secondTextView = TextView(context).apply {
            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            setPadding(dp(4), 0, 0, 0)
        }

        dataLayout.addView(iconView)
        dataLayout.addView(secondTextView)

        addView(dataLayout)
    }

    var headerText: String
        get() = headerTextView.text.toString()
        set(value) {
            headerTextView.text = value
        }

    var headerColor: Int = Color.WHITE
        set(value) {
            field = value
            headerTextView.setTextColor(value)
        }

    var secondText: String
        get() = secondTextView.text.toString()
        set(value) {
            secondTextView.text = value
        }

    var secondColor: Int = Color.WHITE
        set(value) {
            field = value
            secondTextView.setTextColor(value)
            iconView.setColorFilter(value)
        }

    var iconRes: Int = 0
        set(value) {
            field = value
            iconView.setImageResource(value)
        }
}