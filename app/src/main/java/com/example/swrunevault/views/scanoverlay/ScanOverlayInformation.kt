package com.example.swrunevault.views.scanoverlay

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.swrunevault.R
import com.example.swrunevault.UiFactory
import com.example.swrunevault.extensions.colorRes
import com.example.swrunevault.models.Rune
import com.example.swrunevault.models.RuneInnateStat


@SuppressLint("SetTextI18n")
fun createScanOverlayInformation(
        context: Context,
        rune: Rune
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
            3f
        )
        setPadding(4, 8, 4, 8)
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

    //<editor-fold desc="Imagen e Info Principal">
    val headerContainer = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
    }

    //<editor-fold desc="Imagen de la runa">
    val imageRune = UiFactory.icon(
        context,
        context.colorRes(R.color.background_primary),
        rune.runeSet.idRuneResource,
        180,180,
        dp(10), dp(10), dp(10), dp(10))

    headerContainer.addView(imageRune)
    //</editor-fold>

    //<editor-fold desc="Cabecera de la runa">
    val infoContainer = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        weightSum = 4f // Para dividir el espacio en 4
        setPadding(dp(8), 0, 0, 0)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
    }

    // Cantidad de estrellas
    val tvStarts = TextView(context).apply {
        text = "★★★★★★"
        textSize = 12f
        setTextColor(context.colorRes(R.color.orange))
        typeface = Typeface.DEFAULT_BOLD
        setGravity(Gravity.CENTER_VERTICAL)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )
    }

    infoContainer.addView(tvStarts)

    // Set
    val tvTitle = TextView(context).apply {
        text = rune.runeSet.name
        setTextColor(context.colorRes(R.color.purple))
        textSize = 14f
        typeface = Typeface.DEFAULT_BOLD
        setGravity(Gravity.CENTER_VERTICAL)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )
    }
    infoContainer.addView(tvTitle)

    // Rareza
    val tvTag = TextView(context).apply {
        text = rune.rarity.name
        setTextColor(context.colorRes(rune.rarity.colorText))
        setBackgroundColor(context.colorRes(rune.rarity.colorBackground))
        setPadding(10, 4, 10, 4)
        textSize = 10f
        typeface = Typeface.DEFAULT_BOLD
        background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            // Color de fondo del contenedor
            setColor(context.colorRes(rune.rarity.colorBackground))
            // Esquinas redondeadas en píxeles
            cornerRadius = 10f
        }
        setGravity(Gravity.CENTER_VERTICAL)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f)
    }
    infoContainer.addView(tvTag)

    // Slot
    val tvSlot = TextView(context).apply {
        text = "Slot ${rune.slot}"
        setTextColor(Color.WHITE)
        setGravity(Gravity.CENTER_VERTICAL)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )
        textSize = 10f
    }
    infoContainer.addView(tvSlot)

    headerContainer.addView(infoContainer)
    //</editor-fold>

    val propertiesRow = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        weightSum = 2f // Permite dividir el espacio exactamente a la mitad
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    // Columna Izquierda: Propiedad Principal
    val mainPropLayout = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1f)
    }

    mainPropLayout.addView(TextView(context).apply {
        text = "Stat Principal"
        setTextColor(Color.WHITE)
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
        setImageResource(rune.imgMainStat())
        layoutParams = LinearLayout.LayoutParams(50, 50)
        scaleType = ImageView.ScaleType.FIT_CENTER
        setColorFilter(Color.WHITE)
    }
    mainPropData.addView(imgMainIcon)

    mainPropData.addView(TextView(context).apply {
        text = rune.primaryStat()
        setTextColor(Color.WHITE)
        textSize = 13f
        typeface = Typeface.DEFAULT_BOLD
        setPadding(dp(4), 0, 0, 0)
    })
    mainPropLayout.addView(mainPropData)

    propertiesRow.addView(mainPropLayout)

    if(rune.innateStat != RuneInnateStat.UNKNOWN)
    {
        // --- Línea Negra Divisoria Central ---
        val centerVerticalLine = android.view.View(context).apply {
            layoutParams = LinearLayout.LayoutParams(4, LinearLayout.LayoutParams.MATCH_PARENT).apply {
                setMargins(16, 8, 16, 8)
            }
            setBackgroundColor(context.colorRes(R.color.border))
        }
        propertiesRow.addView(centerVerticalLine)

        // Columna Derecha: Propiedad Innata
        val innatePropLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1f)
        }

        innatePropLayout.addView(TextView(context).apply {
            text = "Stat Innate"
            setTextColor(Color.WHITE)
            textSize = 14f
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, 0, 0, dp(8))
        })

        // Contenedor horizontal para Icono + Stats de la Innata
        val innatePropData = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }
        // Icono (Escudo/HP) con fondo naranja muy claro
        val imgInnateIcon = ImageView(context).apply {
            setImageResource(rune.imgInnateStat())
            layoutParams = LinearLayout.LayoutParams(50, 50)
            scaleType = ImageView.ScaleType.FIT_CENTER
            setColorFilter(context.colorRes(rune.getColorByInnateValue()))
        }
        innatePropData.addView(imgInnateIcon)

        innatePropData.addView(TextView(context).apply {
            text = rune.innateStat()
            setTextColor(context.colorRes(rune.getColorByInnateValue()))
            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            setPadding(dp(4), 0, 0, 0)
        })
        innatePropLayout.addView(innatePropData)
        propertiesRow.addView(innatePropLayout)
    }

    headerContainer.addView(propertiesRow)

    mainContainer.addView(headerContainer)
    //</editor-fold>

    // Agregamos el contenedor al panel
    panel.addView(mainContainer)

    return panel
}
