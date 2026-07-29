package com.example.swrunevault.controls

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.swrunevault.R
import com.example.swrunevault.extensions.colorRes
import com.example.swrunevault.extensions.dp

class ProgressStatView(context: Context) : LinearLayout(context) {
    private val titleText: TextView
    private val percentageText: TextView
    private val progressBar: ProgressBar

    init {
        orientation = VERTICAL

        background = GradientDrawable().apply {
            setColor(context.colorRes(R.color.background_primary))
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 16f
        }

        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0,12.dp(context),0,0)
        }

        val header = LinearLayout(context).apply {
            orientation = HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        titleText = TextView(context).apply {
            setTextColor(Color.WHITE)
            textSize = 14f
            typeface = Typeface.DEFAULT_BOLD
        }

        header.addView(titleText)
        addView(header)

        percentageText = TextView(context).apply {
            textSize = 24f
            typeface = Typeface.DEFAULT_BOLD
        }

        addView(percentageText)

        progressBar = ProgressBar(
            context,
            null,
            android.R.attr.progressBarStyleHorizontal
        ).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                16
            )
            max = 100
        }

        addView(progressBar)
    }

    var title: String
        get() = titleText.text.toString()
        set(value) {
            titleText.text = value
        }

    var percentage: Double = 0.0
        @SuppressLint("SetTextI18n")
        set(value) {
            field = value
            percentageText.text = "${value}%"
            progressBar.progress = value.toInt()
        }

    var progressColor: Int = Color.WHITE
        set(value) {
            field = value
            percentageText.setTextColor(value)
            progressBar.progressDrawable.setColorFilter(
                value,
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        }

    var barHeight: Int = progressBar.layoutParams.height
        set(value) {
            progressBar.layoutParams =
                LayoutParams(LayoutParams.MATCH_PARENT, value)
        }
}