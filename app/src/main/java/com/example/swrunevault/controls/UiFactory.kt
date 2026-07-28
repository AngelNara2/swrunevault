package com.example.swrunevault.controls

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.swrunevault.R
import com.example.swrunevault.extensions.colorRes

object UiFactory {

    fun panel(context: Context, weight: Float) : FrameLayout {
        return FrameLayout(context).apply {
            setBackgroundColor(Color.TRANSPARENT)
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                weight
            )
        }
    }

    fun mainContainer(context: Context): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            // Fondo con esquinas y borde
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(
                    context.colorRes(
                        R.color.background_secondary
                    )
                )
                cornerRadius = 24f // Esquinas redondeadas en píxeles
                setStroke( // Borde: grosor en px, color del borde
                    4,
                    context.colorRes(R.color.border)
                )
            }
        }
    }

    fun icon(context: Context, resId: Int, width: Int, height: Int, color_background: Int = Color.TRANSPARENT, color_filter: Int = Color.TRANSPARENT ): FrameLayout {
        return FrameLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(width, height)
            // Fondo con esquinas y borde
            background = GradientDrawable().apply {
                setColor(color_background)
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 16f // Esquinas redondeadas en píxeles
            }
            addView(ImageView(context).apply {
                setImageResource(resId)
                scaleType = ImageView.ScaleType.FIT_CENTER
                setColorFilter(color_filter)
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT)
            })
        }
    }

    fun text(
        context: Context,
        showtext: String,
        size: Float,
        color: Int,
        width: Int = 0,
        height: Int = 0,
        weight: Float = 0f,
        left: Int = 0,
        top: Int = 0,
        right: Int = 0,
        bottom: Int = 0): TextView {
        return TextView(context).apply {
            text = showtext
            textSize = size
            setTextColor(color)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER_VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                width,
                height,
                weight
            ).apply { setMargins(left, top, right, bottom) }
        }
    }

    fun row(context: Context, weightSumItems: Float = 0f): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setBackgroundColor(Color.TRANSPARENT)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            weightSum = weightSumItems
        }
    }

    fun column(context: Context, color_backgorund: Int = Color.TRANSPARENT, weight: Float = 0f): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.TRANSPARENT)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                weight
            )
            background = GradientDrawable().apply {
                setColor(color_backgorund)
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 16f // Esquinas redondeadas en píxeles
            }
        }
    }
}