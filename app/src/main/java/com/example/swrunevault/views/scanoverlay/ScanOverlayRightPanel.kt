package com.example.swrunevault.views.scanoverlay

import android.annotation.SuppressLint
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
import com.example.swrunevault.controls.UiFactory
import com.example.swrunevault.extensions.colorRes
import com.example.swrunevault.models.Rune

@SuppressLint("SetTextI18n")
fun createScanOverlayRightPanel(
    context: Context,
    rune: Rune,
    onClose: () -> Unit,
    onRemove: () -> Unit
): FrameLayout {

    // FUNCIÓN AUXILIAR: Convierte valores DP a Píxeles reales según la pantalla del dispositivo
    val density = context.resources.displayMetrics.density
    fun dp(value: Int): Int = (value * density).toInt()

    // Panel principal para contener todos los elementos
    val panel = FrameLayout(context).apply {
        setBackgroundColor(Color.TRANSPARENT)
        layoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT,
            1f
        )
        setPadding(4, 8, 8, 8)
    }

    // Contenedor Vertical principal para estructurar las secciones de arriba a abajo
    val mainContainer = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(8),dp(8),dp(8),dp(8))
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        // Fondo con esquinas y borde
        background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(context.colorRes(R.color.background_secondary))
            cornerRadius = 24f // Esquinas redondeadas en píxeles
            setStroke(4, context.colorRes(R.color.border)) // Borde: grosor en px, color del borde
        }
    }

    val titleContainer = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
    }

    val imgEficienciaIcon = ImageView(context).apply {
        setImageResource(R.drawable.icon_efficiency)
        layoutParams = LinearLayout.LayoutParams(50, 50) // Escalado a DP
        scaleType = ImageView.ScaleType.FIT_CENTER
        setColorFilter(Color.WHITE)
    }
    titleContainer.addView(imgEficienciaIcon)

    val tvSectionTitle = TextView(context).apply {
        text = "EFICIENCIA"
        setTextColor(Color.WHITE)
        textSize = 15f // Las fuentes en Android ya se auto-escalan de forma nativa (SP)
        typeface = Typeface.DEFAULT_BOLD
        setPadding(dp(4),0,0,0)
    }
    titleContainer.addView(tvSectionTitle)
    mainContainer.addView(titleContainer)

    // Línea divisoria debajo del título
    val topDivisor = android.view.View(context).apply {
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp(1)).apply {
            setMargins(0, dp(8), 0, dp(8))
        }
        setBackgroundColor(context.colorRes(R.color.border))
    }
    mainContainer.addView(topDivisor)

    val baseCard = UiFactory.progressbar(
        context,
        "Eficiencia Base",
        14f,
        rune.currentEfficiency(),
        24f,
        context.colorRes(R.color.white),
        dp(6)
    ).apply { setPadding(dp(8), dp(8), dp(8), dp(8)) }
    mainContainer.addView(baseCard)

    // Espaciador adaptativo entre tarjetas
    mainContainer.addView(android.view.View(context).apply { layoutParams = LinearLayout.LayoutParams(1, dp(12)) })

    val actualCard = UiFactory.progressbar(
        context,
        "Eficiencia Actual",
        14f,
        rune.currentEfficiency(),
        24f,
        context.colorRes(R.color.purple),
        dp(6)
    ).apply { setPadding(dp(8), dp(8), dp(8), dp(8)) }
    mainContainer.addView(actualCard)

    // Espaciador adaptativo entre tarjetas
    mainContainer.addView(android.view.View(context).apply { layoutParams = LinearLayout.LayoutParams(1, dp(12)) })


    val maxCard = UiFactory.progressbar(
        context,
        "Eficiencia Máxima",
        14f,
        rune.maxEfficiency(),
        24f,
        context.colorRes(R.color.orange),
        dp(6)
    ).apply { setPadding(dp(8), dp(8), dp(8), dp(8)) }
    mainContainer.addView(maxCard)

    panel.addView(mainContainer)

    return panel
}