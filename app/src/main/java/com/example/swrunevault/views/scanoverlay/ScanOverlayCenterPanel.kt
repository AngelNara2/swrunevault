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
        setBackgroundColor("#F0F0F0".toColorInt()) // Cambiado de GREEN a WHITE
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
            setColor("#F0F0F0".toColorInt())
            cornerRadius = 24f // Esquinas redondeadas en píxeles
            setStroke(4, Color.LTGRAY) // Borde: grosor en px, color del borde
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
        setTextColor(Color.BLACK)
        textSize = 14f
        setPadding(0, 0, 0, dp(8))
    })

    // Contenedor horizontal para Icono + Stats del Main
    val mainPropData = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
    }

    // Icono (Espada/ATQ) con fondo morado muy claro
    val imgMainIcon = ImageView(context).apply {
        setBackgroundColor("#F3E8FF".toColorInt()) // Fondo lila claro
        // setImageResource(R.drawable.ic_atq)
        layoutParams = LinearLayout.LayoutParams(70, 70)
    }
    mainPropData.addView(imgMainIcon)

    mainPropData.addView(TextView(context).apply {
        text = rune.primaryStat()
        setTextColor("#1A237E".toColorInt())
        textSize = 14f
        typeface = Typeface.DEFAULT_BOLD
        setPadding(dp(8), 0, 0, 0)
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
            setBackgroundColor(Color.BLACK)
        }
        propertiesRow.addView(centerVerticalLine)

        // Columna Derecha: Propiedad Innata
        val innatePropLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1f)
        }

        innatePropLayout.addView(TextView(context).apply {
            text = "Stat Innate"
            setTextColor(Color.BLACK)
            textSize = 14f
            setPadding(0, 0, 0, dp(8))
        })

        // Contenedor horizontal para Icono + Stats de la Innata
        val innatePropData = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }
        // Icono (Escudo/HP) con fondo naranja muy claro
        val imgInnateIcon = ImageView(context).apply {
            setBackgroundColor("#FFF3E0".toColorInt()) // Fondo naranja claro
            // setImageResource(R.drawable.ic_hp)
            layoutParams = LinearLayout.LayoutParams(70, 70)
        }
        innatePropData.addView(imgInnateIcon)

        innatePropData.addView(TextView(context).apply {
            text = rune.innateStat()
            setTextColor("#1A237E".toColorInt())
            textSize = 14f
            typeface = Typeface.DEFAULT_BOLD
            setPadding(dp(8), 0, 0, 0)
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
        setBackgroundColor(Color.BLACK)
    }
    mainContainer.addView(middleDivisor)

    //<editor-fold desc="Sub propiedades">
    mainContainer.addView(TextView(context).apply {
        text = "Sub Propiedades"
        setTextColor(Color.DKGRAY)
        textSize = 14f
        setPadding(0, 0, 0, dp(8))
    })

    // Contenedor gris/lila muy claro para encerrar la lista de sub-propiedades
    val subPropertiesCard = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        setBackgroundColor("#F8F9FA".toColorInt()) // Fondo sutil para la "tarjeta"
        //setPadding(24, 16, 24, 16)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    rune.subStats.forEachIndexed { index, subStat ->
        val row = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            //setPadding(0, 16, 0, 16)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            weightSum = 4f
        }

        // Icono de la estadística
        val leftLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2f)
            addView(ImageView(context).apply {
                setBackgroundColor(Color.GRAY) // Placeholder para el icono de la estadística
                layoutParams = LinearLayout.LayoutParams(35, 35)
            })
        }

        // Nombre del sub stat
        val tvSubName = TextView(context).apply {
            text = subStat.statType?.displayName
            setTextColor(Color.BLACK)
            typeface = Typeface.DEFAULT_BOLD
        }
        leftLayout.addView(tvSubName)
        row.addView(leftLayout)

        // Valor actual del sub stat
        val tvSubValue = TextView(context).apply {
            text = "${subStat.value}${if(subStat.statType?.isPercentage == true) "%" else ""}"
            setTextColor("#4A148C".toColorInt()) // Color morado vivo para los números procesados
            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.END
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                (if(subStat.increment == 0) 2f else 1f))
        }
        row.addView(tvSubValue)

        // Si el valor actual de la piedra de la piedra de molino es mayor a cero
        // se agrega el texto del valor base + su incremento
        if(subStat.increment != 0)
        {
            val tvCurrentGrindstone = TextView(context).apply {
                text = "${subStat.value+subStat.increment}${if(subStat.statType?.isPercentage == true) "%" else ""}"
                setTextColor(Color.GRAY)
                textSize = 13f
                gravity = Gravity.END
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
            }
            row.addView(tvCurrentGrindstone)
        }

        subPropertiesCard.addView(row)

        // Agregar una mini línea divisoria gris entre cada fila, excepto en la última
        if (index < rune.subStats.size - 1) {
            val innerDivisor = android.view.View(context).apply {
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1)
                setBackgroundColor(Color.BLACK)
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