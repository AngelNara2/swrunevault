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

@SuppressLint("SetTextI18n")
fun createScanOverlayCenterPanel(
    context: Context,
    rune: Rune
): FrameLayout {
    // FUNCIÓN AUXILIAR: Convierte valores DP a Píxeles reales según la pantalla del dispositivo
    val density = context.resources.displayMetrics.density
    fun dp(value: Int): Int = (value * density).toInt()

    // 1. Panel Principal (Contenedor base)
    val panel = FrameLayout(context).apply {
        setBackgroundColor(Color.WHITE) // Cambiado de GREEN a WHITE
        layoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT,
            1f
        )
        setPadding(4, 8, 4, 8)
    }

    // Contenedor vertical que almacena todo el contenido del panel central
    val mainContainer = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(8), dp(8), dp(8), dp(8))
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        // 1. Crear el fondo con esquinas y borde
        background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE

            // Configurar el color de fondo del contenedor (ej. Blanco)
            setColor(Color.WHITE)

            // Configurar las esquinas redondeadas (en píxeles)
            cornerRadius = 24f

            // Configurar el borde: (grosor en px, color del borde)
            setStroke(4, Color.LTGRAY)
        }
    }

    // ==========================================
    // TITULO: PROPIEDADES
    // ==========================================
    val titleContainer = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
    }

    // Icono del título de propiedades
    val imgPropiedadesIcon = ImageView(context).apply {
        // setImageResource(R.drawable.ic_propiedades) // Tu icono aquí
        setBackgroundColor("#5E24B3".toColorInt()) // Placeholder visual temporal
        layoutParams = LinearLayout.LayoutParams(40, 40)
    }
    titleContainer.addView(imgPropiedadesIcon)

    val tvSectionTitle = TextView(context).apply {
        text = "PROPIEDADES"
        setTextColor(Color.BLACK)
        textSize = 16f
        typeface = Typeface.DEFAULT_BOLD
        //setPadding(16, 0, 0, 0)
    }
    titleContainer.addView(tvSectionTitle)
    mainContainer.addView(titleContainer)

    // Línea divisoria sutil debajo del título principal
    val topDivisor = android.view.View(context).apply {
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2).apply {
            setMargins(0, 16, 0, 16)
        }
        setBackgroundColor("#E8E8E8".toColorInt())
    }
    mainContainer.addView(topDivisor)

    // ==========================================
    // SECCIÓN: PROPIEDAD PRINCIPAL E INNATA (Dividido 50/50)
    // ==========================================
    val propertiesRow = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        weightSum = 2f // Permite dividir el espacio exactamente a la mitad
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    // --- Columna Izquierda: Propiedad Principal ---
    val mainPropLayout = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
    }

    mainPropLayout.addView(TextView(context).apply {
        text = "Propiedad Principal"
        setTextColor(Color.GRAY)
        textSize = 14f
    })

    // Contenedor horizontal para Icono + Stats del Main
    val mainPropData = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        //setPadding(0, 12, 0, 12)
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
        textSize = 20f
        typeface = Typeface.DEFAULT_BOLD
        //setPadding(16, 0, 0, 0)
    })
    mainPropLayout.addView(mainPropData)
    propertiesRow.addView(mainPropLayout)

    // --- Línea Negra Divisoria Central ---
    val centerVerticalLine = android.view.View(context).apply {
        layoutParams = LinearLayout.LayoutParams(4, LinearLayout.LayoutParams.MATCH_PARENT).apply {
            setMargins(16, 8, 16, 8)
        }
        setBackgroundColor(Color.BLACK)
    }
    propertiesRow.addView(centerVerticalLine)

    // --- Columna Derecha: Propiedad Innata ---
    val innatePropLayout = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
            setMargins(16, 0, 0, 0)
        }
    }

    innatePropLayout.addView(TextView(context).apply {
        text = "Propiedad Innata"
        setTextColor(Color.GRAY)
        textSize = 14f
    })

    // Contenedor horizontal para Icono + Stats de la Innata
    val innatePropData = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        //setPadding(0, 12, 0, 12)
    }
    // Icono (Escudo/HP) con fondo naranja muy claro
    val imgInnateIcon = ImageView(context).apply {
        setBackgroundColor("#FFF3E0".toColorInt()) // Fondo naranja claro
        // setImageResource(R.drawable.ic_hp)
        layoutParams = LinearLayout.LayoutParams(70, 70)
    }
    innatePropData.addView(imgInnateIcon)

    innatePropData.addView(TextView(context).apply {
        text = rune.innateStat() // Reemplazar dinámicamente con rune.innateStat
        setTextColor("#1A237E".toColorInt())
        textSize = 20f
        typeface = Typeface.DEFAULT_BOLD
        //setPadding(16, 0, 0, 0)
    })
    innatePropLayout.addView(innatePropData)
    propertiesRow.addView(innatePropLayout)

    mainContainer.addView(propertiesRow)

    // Línea divisoria debajo de las propiedades superiores
    val middleDivisor = android.view.View(context).apply {
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2).apply {
            setMargins(0, 16, 0, 24)
        }
        setBackgroundColor("#E8E8E8".toColorInt())
    }
    mainContainer.addView(middleDivisor)

    // ==========================================
    // SECCIÓN: SUB PROPIEDADES (Lista con Fondo Tenue)
    // ==========================================
    mainContainer.addView(TextView(context).apply {
        text = "Sub Propiedades"
        setTextColor(Color.DKGRAY)
        textSize = 15f
        typeface = Typeface.DEFAULT_BOLD
        //setPadding(0, 0, 0, 16)
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

    // Datos simulados extraídos de tu imagen (reemplazar luego con un loop si `rune.subStats` es una lista)
    val subsData = listOf(
        Triple("VEL", "+6", "1 roll"),
        Triple("Tasa CRÍ", "+12%", "2 rolls"),
        Triple("Daño CRÍ", "+5%", "1 roll"),
        Triple("HP", "+7%", "1 roll")
    )

    rune.subStats.forEachIndexed { index, subStat ->
        val row = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            //setPadding(0, 16, 0, 16)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        // 1. Icono e Identificador de la estadística (Columna Izquierda con Peso para empujar el resto)
        val leftLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val subIcon = ImageView(context).apply {
            setBackgroundColor(Color.GRAY) // Placeholder para el icono de la estadística
            layoutParams = LinearLayout.LayoutParams(35, 35)
        }
        leftLayout.addView(subIcon)

        val tvSubName = TextView(context).apply {
            text = subStat.statType?.displayName
            setTextColor(Color.BLACK)
            typeface = Typeface.DEFAULT_BOLD
            //setPadding(16, 0, 0, 0)
        }
        leftLayout.addView(tvSubName)
        row.addView(leftLayout)

        // 2. Valor numérico central (Morado/Azul eléctrico en tu imagen)
        val tvSubValue = TextView(context).apply {
            text = "${subStat.value} ${if(subStat.statType?.isPercentage == true) "%" else ""}"
            setTextColor("#4A148C".toColorInt()) // Color morado vivo para los números procesados
            textSize = 16f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 0, 48, 0) }
        }
        row.addView(tvSubValue)

        // 3. Cantidad de Rolls (Columna Derecha)
        /*val tvSubRolls = TextView(context).apply {
            text = "rolls"
            setTextColor(Color.GRAY)
            textSize = 13f
            gravity = Gravity.END
        }
        row.addView(tvSubRolls)*/

        subPropertiesCard.addView(row)

        // Agregar una mini línea divisoria gris entre cada fila, excepto en la última
        if (index < subsData.size - 1) {
            val innerDivisor = android.view.View(context).apply {
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1)
                setBackgroundColor("#EAEAEA".toColorInt())
            }
            subPropertiesCard.addView(innerDivisor)
        }
    }

    mainContainer.addView(subPropertiesCard)

    // Finalmente, inyectamos el contenedor completo al FrameLayout raíz
    panel.addView(mainContainer)

    return panel
}