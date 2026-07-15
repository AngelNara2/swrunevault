package com.example.swrunevault.controls

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
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
        weight: Float = 0f): TextView {
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
            )
        }
    }

    fun stat(context: Context, headerText: String, headerColor:Int, resId: Int, secondText: String, secondColor:Int): LinearLayout {
        val density = context.resources.displayMetrics.density
        fun dp(value: Int): Int = (value * density).toInt()

        val mainPropLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f)
        }

        mainPropLayout.addView(TextView(context).apply {
            text = headerText
            setTextColor(headerColor)
            textSize = 14f
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, 0, 0, dp(8))
        })

        // Contenedor horizontal para Icono + Stats del Main
        val mainPropData = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        // Icono del stat principal
        val imgMainIcon = ImageView(context).apply {
            setImageResource(resId)
            layoutParams = LinearLayout.LayoutParams(50, 50)
            scaleType = ImageView.ScaleType.FIT_CENTER
            setColorFilter(secondColor)
        }
        mainPropData.addView(imgMainIcon)

        mainPropData.addView(TextView(context).apply {
            text = secondText
            setTextColor(secondColor)
            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            setPadding(dp(4), 0, 0, 0)
        })
        mainPropLayout.addView(mainPropData)

        return mainPropLayout
    }

    fun line(context: Context, width: Int, height: Int,color: Int = Color.BLACK , left: Int, top: Int, right: Int, bottom: Int): View{
        return View(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                width,
                height).apply {
                    setMargins(left, top, right, bottom)
                }
            setBackgroundColor(color)
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

    fun button(
        context: Context,
        idIcon: Int,
        iconColor: Int = Color.BLACK,
        showText: String,
        size: Float,
        primaryColor: Int,
        secondaryColor: Int,
        shadowSizeText: Float = 0f,
        shadowColorText: Int = Color.TRANSPARENT,
        gradientBackground: IntArray = intArrayOf()
    ): LinearLayout {
        val density = context.resources.displayMetrics.density
        fun dp(value: Int): Int = (value * density).toInt()

        val btn = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
            background = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                gradientBackground
            ).apply {
                if(gradientBackground.count() == 0) {setColor(secondaryColor)}
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 16f
            }
            setPadding(dp(8), dp(8), dp(8), dp(8))
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            isClickable = true
            isFocusable = true
        }

        btn.addView(
            ImageView(context).apply {
                setImageResource(idIcon)
                layoutParams = LinearLayout.LayoutParams(dp(22), dp(22))
                scaleType = ImageView.ScaleType.FIT_CENTER
                setColorFilter(iconColor)
            }
        )

        btn.addView(line(context,
            4,
            LinearLayout.LayoutParams.MATCH_PARENT,
            Color.TRANSPARENT,
            2, 0, 2, 0
        ))

        val txtEscanearContainer = LinearLayout(context).apply {orientation = LinearLayout.VERTICAL}

        txtEscanearContainer.addView(
            StrokeTextView(context).apply {
                text = showText
                textSize = size
                setTextColor(primaryColor)
                strokeColor = shadowColorText
                strokeWidth = shadowSizeText
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }
        )

        btn.addView(txtEscanearContainer)

        return btn
    }
}