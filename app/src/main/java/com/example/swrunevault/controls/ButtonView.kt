package com.example.swrunevault.controls

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.swrunevault.extensions.dp

class ButtonView(context: Context) : LinearLayout(context) {

    private val iconView = ImageView(context).apply {
        layoutParams = LayoutParams(
            22.dp(context),
            22.dp(context)
        )
        scaleType = ImageView.ScaleType.FIT_CENTER
    }

    private val textView = StrokeTextView(context).apply {
        layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
    }

    private val textContainer = LinearLayout(context).apply {
        orientation = VERTICAL
    }

    init {
        orientation = HORIZONTAL

        gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL

        setPadding(
            8.dp(context),
            8.dp(context),
            8.dp(context),
            8.dp(context)
        )

        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT,
            1f
        )

        isClickable = true
        isFocusable = true

        // Icono
        addView(iconView)

        // Separador
        addView(
            DividerView(context).apply {
                setup(
                    LineOrientation.VERTICAL,
                    4,
                    Color.TRANSPARENT,
                    left = 2,
                    right = 2
                )
            }
        )

        // Texto
        textContainer.addView(textView)
        addView(textContainer)
    }

    var idIcon: Int = 0
        set(value) {
            field = value
            iconView.setImageResource(value)
        }

    var iconColor: Int = Color.BLACK
        set(value) {
            field = value
            iconView.setColorFilter(value)
        }

    var showText: String = ""
        set(value) {
            field = value
            textView.text = value
        }

    var size: Float = 14f
        set(value) {
            field = value
            textView.textSize = value
        }

    var primaryColor: Int = Color.BLACK
        set(value) {
            field = value
            textView.setTextColor(value)
        }

    var secondaryColor: Int = Color.WHITE
        set(value) {
            field = value
            updateBackground()
        }

    var shadowSizeText: Float = 0f
        set(value) {
            field = value
            textView.strokeWidth = value
        }

    var shadowColorText: Int = Color.TRANSPARENT
        set(value) {
            field = value
            textView.strokeColor = value
        }

    var gradientBackground: IntArray = intArrayOf()
        set(value) {
            field = value
            updateBackground()
        }

    private fun updateBackground() {
        background = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            gradientBackground
        ).apply {
            if (gradientBackground.isEmpty()) {
                setColor(secondaryColor)
            }
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 16f
        }
    }
}