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
    val panel = UiFactory.panel(context,
        3f).apply {
            setPadding(4, 8, 4, 8)
    }

    // Contenedor Vertical principal para estructurar las secciones de arriba a abajo
    val mainContainer = UiFactory.mainContainer(context).apply {
        setPadding(dp(8),dp(8),dp(8),dp(8))
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
        180,180
        ).apply {
            setPadding(dp(10), dp(10), dp(10), dp(10))
        }
    headerContainer.addView(imageRune)
    //</editor-fold>

    //<editor-fold desc="Cabecera de la runa">
    val infoContainer = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        weightSum = 4f // Para dividir el espacio en 4
        setPadding(dp(8), 0, 0, 0)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            1f
        )
    }

    // Cantidad de estrellas
    val tvStarts = UiFactory.text(context,
        "★★★★★★",
        12f,
        context.colorRes(R.color.orange),
        1f
    )
    infoContainer.addView(tvStarts)

    // Set
    val tvTitle = UiFactory.text(context,
        rune.runeSet.name,
        14f,
        context.colorRes(R.color.purple),
        1f
    )
    infoContainer.addView(tvTitle)

    // Rareza
    val tvTag = UiFactory.text(context,
        rune.rarity.name,
        10f,
        context.colorRes(rune.rarity.colorText),
        1f).apply {
        setPadding(10, 4, 10, 4)
        background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            // Color de fondo del contenedor
            setColor(context.colorRes(rune.rarity.colorBackground))
            // Esquinas redondeadas en píxeles
            cornerRadius = 10f
        }
    }
    infoContainer.addView(tvTag)

    // Slot
    val tvSlot = UiFactory.text(context,
        "Slot ${rune.slot}",
        10f,
        Color.WHITE,
        1f
    )
    infoContainer.addView(tvSlot)

    headerContainer.addView(infoContainer)
    //</editor-fold>

    val verticalLine = UiFactory.line(context,
        4,
        LinearLayout.LayoutParams.MATCH_PARENT,
        16, 0, 16, 0
    )
    headerContainer.addView(verticalLine)

    //<editor-fold desc="Propiedad principal e innate">
    val propertiesRow = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        weightSum = 2f // Permite dividir el espacio exactamente a la mitad
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    // Columna Izquierda: Propiedad Principal
    val mainStat = UiFactory.stat(context,
        "Stat Principal",
        Color.WHITE,
        rune.imgMainStat(),
        rune.primaryStat(),
        Color.WHITE
    )
    propertiesRow.addView(mainStat)

    if(rune.innateStat != RuneInnateStat.UNKNOWN)
    {
        // --- Línea Negra Divisoria Central ---
        val verticalLine = UiFactory.line(context,
            4,
            LinearLayout.LayoutParams.MATCH_PARENT,
            16, 0, 16, 0
        )
        propertiesRow.addView(verticalLine)

        // Columna Derecha: Propiedad Innata
        val innateStat = UiFactory.stat(context,
            "Stat Innate",
            Color.WHITE,
            rune.imgInnateStat(),
            rune.innateStat(),
            context.colorRes(rune.getColorByInnateValue())
            )
        propertiesRow.addView(innateStat)
    }

    headerContainer.addView(propertiesRow)
    //</editor-fold>

    mainContainer.addView(headerContainer)
    //</editor-fold>

    // Línea divisoria
    val horizontalLine1 = UiFactory.line(
        context,
        LinearLayout.LayoutParams.MATCH_PARENT,
        4,
        0, dp(8), 0, dp(8)
    )
    mainContainer.addView(horizontalLine1)

    //<editor-fold desc="SubPropiedades">
    val subPropertiesCard = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        background = GradientDrawable().apply {
            setColor(context.colorRes(R.color.background_primary))
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 16f // Esquinas redondeadas en píxeles
        }
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    val lowSubStatContribution = rune.subStats.minByOrNull { it.subStatCurrentContribution() }
    val hasSubStatEnchanted = rune.subStats.any {it.statType.isEnchanted}

    // Cargar los subStats de la runa
    rune.subStats.forEachIndexed { index, subStat ->
        val row = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setBackgroundColor(Color.TRANSPARENT)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(dp(8),dp(8),dp(8),dp(8))
            weightSum = 5f
        }

        val colorSubStat =
            if((subStat == lowSubStatContribution) and (!hasSubStatEnchanted))
                context.colorRes(R.color.light_red)
            else
                if(!subStat.statType.isEnchanted)
                    Color.WHITE
                else
                    context.colorRes(R.color.orange)

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
                layoutParams = FrameLayout.LayoutParams(50,50)
                scaleType = ImageView.ScaleType.FIT_CENTER
                setColorFilter(colorSubStat)
            })
        }

        // Nombre del sub stat
        val tvSubName = TextView(context).apply {
            text = subStat.statType?.displayText
            setTextColor(colorSubStat)

            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            setPadding(dp(4),0,0,0)
        }
        leftLayout.addView(tvSubName)
        row.addView(leftLayout)

        // Valor actual del sub stat
        val tvSubValue = TextView(context).apply {
            text = "${subStat.value}${if(subStat.statType?.isPercentage == true) "%" else ""}"
            setTextColor(colorSubStat)
            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
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
            setTextColor(context.colorRes(subStat.getColorByValueGrinstone()))
            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }

        row.addView(tvGrindstone)

        val tvTotal = TextView(context).apply {
            text = "${subStat.value+grindstoneValue}${if(subStat.statType?.isPercentage == true) "%" else ""}"
            setTextColor(context.colorRes(R.color.orange))
            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER_HORIZONTAL
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
    //<editor-fold>

    // Agregamos el contenedor al panel
    panel.addView(mainContainer)

    return panel
}
