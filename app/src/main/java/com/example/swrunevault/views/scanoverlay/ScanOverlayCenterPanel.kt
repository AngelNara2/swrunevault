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
import androidx.core.graphics.toColorInt
import com.example.swrunevault.R
import com.example.swrunevault.extensions.colorRes
import com.example.swrunevault.models.Rune
import com.example.swrunevault.models.RuneInnateStat

@SuppressLint("SetTextI18n")
fun createScanOverlayCenterPanel(
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
            1f
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

    //<editor-fold desc="Propiedad principal e innate">
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

    // Icono (Espada/ATQ) con fondo morado muy claro
    val imgMainIcon = ImageView(context).apply {
        setImageResource(rune.imgMainStat())
        layoutParams = LinearLayout.LayoutParams(35, 35)
        scaleType = ImageView.ScaleType.FIT_CENTER
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
            layoutParams = LinearLayout.LayoutParams(35, 35)
            scaleType = ImageView.ScaleType.FIT_CENTER
        }
        innatePropData.addView(imgInnateIcon)

        innatePropData.addView(TextView(context).apply {
            text = rune.innateStat()
            setTextColor(Color.WHITE)
            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            setPadding(dp(4), 0, 0, 0)
        })
        innatePropLayout.addView(innatePropData)
        propertiesRow.addView(innatePropLayout)
    }

    mainContainer.addView(propertiesRow)
    //</editor-fold>

    // Línea divisoria debajo de las propiedades superiores
    val middleDivisor = android.view.View(context).apply {
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2).apply {
            setMargins(0, dp(8), 0, dp(8))
        }
        setBackgroundColor(context.colorRes(R.color.border))
    }
    mainContainer.addView(middleDivisor)

    //<editor-fold desc="Sub propiedades">
    mainContainer.addView(TextView(context).apply {
        text = "Sub Propiedades"
        setTextColor(Color.WHITE)
        textSize = 14f
        typeface = Typeface.DEFAULT_BOLD
        setPadding(0, 0, 0, dp(8))
    })

    // Contenedor gris/lila muy claro para encerrar la lista de sub-propiedades
    val subPropertiesCard = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        setBackgroundColor(Color.TRANSPARENT)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    // Cargar los subStats de la runa
    rune.subStats.forEachIndexed { index, subStat ->
        val row = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setBackgroundColor(context.colorRes(R.color.background_primary))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(dp(8),dp(8),dp(8),dp(8))
            weightSum = 5f
        }

        // Icono de la estadística
        val leftLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2f)
            addView(ImageView(context).apply {
                setImageResource(subStat.imgStat())
                layoutParams = FrameLayout.LayoutParams(35,35)
                scaleType = ImageView.ScaleType.FIT_CENTER
            })
        }

        // Nombre del sub stat
        val tvSubName = TextView(context).apply {
            text = subStat.statType?.displayText
            setTextColor(Color.WHITE)
            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            setPadding(dp(4),0,0,0)
        }
        leftLayout.addView(tvSubName)
        row.addView(leftLayout)

        // Valor actual del sub stat
        val tvSubValue = TextView(context).apply {
            text = "${subStat.value}${if(subStat.statType?.isPercentage == true) "%" else ""}"
            setTextColor(Color.WHITE)
            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.END
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }
        row.addView(tvSubValue)

        val grindstoneValue = if((subStat.statType?.hasGrindsTone == true) and (subStat.grindstonevalue == 0)) (subStat.statType?.grindstoneMaxValue?:0) else subStat.grindstonevalue

        val tvGrindstone = TextView(context).apply {
            text =
                if(subStat.statType?.hasGrindsTone == true)
                    "${grindstoneValue}${if(subStat.statType?.isPercentage == true) "%" else ""}"
                else
                    "-"
            setTextColor(subStat.getColorByValue().toColorInt())
            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.END
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }

        row.addView(tvGrindstone)

        val tvTotal = TextView(context).apply {
            text = "${subStat.value+grindstoneValue}${if(subStat.statType?.isPercentage == true) "%" else ""}"
            setTextColor(context.colorRes(R.color.purple))
            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.END
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }
        row.addView(tvTotal)

        subPropertiesCard.addView(row)

        // Agregar una mini línea divisoria gris entre cada fila, excepto en la última
        if (index < rune.subStats.size - 1) {
            val innerDivisor = android.view.View(context).apply {
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2)
                setBackgroundColor(context.colorRes(R.color.border))
            }
            subPropertiesCard.addView(innerDivisor)
        }
    }

    mainContainer.addView(subPropertiesCard)
    //</editor-fold>

    // Agregamos el contenedor al panel
    panel.addView(mainContainer)

    return panel
}