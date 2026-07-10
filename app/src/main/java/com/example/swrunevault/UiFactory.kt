package com.example.swrunevault

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.swrunevault.extensions.colorRes

object UiFactory {

    fun icon(context: Context, color: Int, resId: Int, width: Int, height: Int, left: Int, top: Int, right: Int, bottom: Int): FrameLayout{
        return FrameLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(width, height)
            // Fondo con esquinas y borde
            background = GradientDrawable().apply {
                setColor(color)
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 16f // Esquinas redondeadas en píxeles
            }
            addView(ImageView(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT)
                scaleType = ImageView.ScaleType.FIT_CENTER
                setImageResource(resId)
                setPadding(left, top, right, bottom)
            })
        }
    }

    fun horizontalLine(context: Context, color: Int,height: Int, left: Int, top: Int, right: Int, bottom: Int): android.view.View{
        return android.view.View(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                height).apply {
                    setMargins(left, top, right, bottom)
                }
            setBackgroundColor(context.colorRes(color))
        }
    }
}