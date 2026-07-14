package com.example.swrunevault.views.scanoverlay

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.FrameLayout
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
    val panel = UiFactory.panel(context, 3f).apply {
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
        rune.runeSet.idRuneResource,
        180,
        180,
        context.colorRes(R.color.background_primary),
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
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT,
        1f
    )
    infoContainer.addView(tvStarts)

    // Set
    val tvTitle = UiFactory.text(context,
        rune.runeSet.name,
        14f,
        context.colorRes(R.color.purple),
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT,
        1f
    )
    infoContainer.addView(tvTitle)

    // Rareza
    val tvTag = UiFactory.text(context,
        rune.rarity.name,
        10f,
        context.colorRes(rune.rarity.colorText),
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT,
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
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT,
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
        // Línea Negra Divisoria Central
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

    // SubStat con la menor contribución de la runa
    val lowSubStatContribution = rune.subStats.minByOrNull { it.subStatCurrentContribution() }

    // La runa tiene una propiedad que viene una gema
    val hasSubStatEnchanted = rune.subStats.any {it.statType.isEnchanted}

    //<editor-fold desc="Encabezados">
    val rowColumNames = UiFactory.row(context,5f).apply {
        setPadding(dp(8),dp(8),dp(8),dp(8))
    }

    // SubStat
    rowColumNames.addView(UiFactory.text(context,
        "SubStat",
        13f,
        Color.WHITE,
        0,
        LinearLayout.LayoutParams.WRAP_CONTENT,
        2f
    )
    )

    // Base
    rowColumNames.addView(UiFactory.text(context,
        "Base",
        13f,
        Color.WHITE,
        0,
        LinearLayout.LayoutParams.WRAP_CONTENT,
        1f
    ).apply { gravity = Gravity.CENTER }
    )

    // GrindStone
    rowColumNames.addView(UiFactory.text(context,
        "GrindStone",
        13f,
        Color.WHITE,
        0,
        LinearLayout.LayoutParams.WRAP_CONTENT,
        1f
    ).apply { gravity = Gravity.CENTER }
    )

    // Total
    rowColumNames.addView(UiFactory.text(context,
        "Total",
        13f,
        Color.WHITE,
        0,
        LinearLayout.LayoutParams.WRAP_CONTENT,
        1f
    ).apply { gravity = Gravity.CENTER }
    )

    subPropertiesCard.addView(rowColumNames)
    //</editor-fold>

    val horizontalLine2 = UiFactory.line(
        context,
        LinearLayout.LayoutParams.MATCH_PARENT,
        4,
        dp(8), 0, dp(8), 0
    )
    subPropertiesCard.addView(horizontalLine2)

    // Cargar los subStats de la runa
    rune.subStats.forEachIndexed { index, subStat ->
        val rowValues = UiFactory.row(context,5f).apply {
            setPadding(dp(8),dp(8),dp(8),dp(8))
        }

        // Color que va a tener el icono y SubStat
        val colorSubStat =
            // Si el SubStat es el más bajo y ninguna propiedad proviene de una gema, será rojo
            if((subStat == lowSubStatContribution) and (!hasSubStatEnchanted))
                context.colorRes(R.color.light_red)
            else
                // Si el SubStat no proviene de una gema, será blanco
                if(!subStat.statType.isEnchanted)
                    Color.WHITE
                // En caso contrario será naranja, indicando que proviene de una gema
                else
                    context.colorRes(R.color.orange)

        // Fila del SubStat
        val rowSubStat = UiFactory.row(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2f)
        }

        val iconoSub = UiFactory.icon(
            context,
            subStat.imgStat(),
            50,
            50,
            color_filter = colorSubStat,
        )

        rowSubStat.addView(iconoSub)

        /*val leftLayout = LinearLayout(context).apply {
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
        }*/

        // Nombre
        val tvSubName = TextView(context).apply {
            text = subStat.statType?.displayText
            setTextColor(colorSubStat)
            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            setPadding(dp(10),0,0,0)
        }
        rowSubStat.addView(tvSubName)
        rowValues.addView(rowSubStat)

        // Valor actual
        val tvSubValue = TextView(context).apply {
            text = subStat.textValueStat()
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
        rowValues.addView(tvSubValue)

        // Indica si es necesario mostrar los valores máximos si su valor es menor
        val showMaxValue =  subStat.hasGrindstone() and !subStat.hasMaxGrindstoneValue()

        // Fila que contiene el valor de su Grindstone actual y su maximo posible
        val rowGrindstoneValues = UiFactory.row(context,0f).apply {
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }

        // Valor del Grindstone obtenido o posible de obtener
        val tvGrindstone = TextView(context).apply {
            text = subStat.textGrindstoneValue()
            setTextColor(context.colorRes(subStat.getColorByValueGrinstone()))
            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            gravity = if(showMaxValue) Gravity.END else Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }
        rowGrindstoneValues.addView(tvGrindstone)

        if(showMaxValue){
            // Valor maximo del Grindstone obtenido o posible de obtener
            val tvGrindstoneMaxValue = TextView(context).apply {
                text = subStat.textGrindstoneMaxValue()
                setTextColor(context.colorRes(R.color.green))
                textSize = 13f
                typeface = Typeface.DEFAULT_BOLD
                gravity = Gravity.START
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
            }
            rowGrindstoneValues.addView(tvGrindstoneMaxValue)
        }

        rowValues.addView(rowGrindstoneValues)

        // Fila que contiene el valor de su Total actual y su maximo posible
        val rowTotalValues = UiFactory.row(context,0f).apply {
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }

        // Valor del Total obtenido actual
        val tvTotal = TextView(context).apply {
            text = subStat.textTotalValue()
            setTextColor(context.colorRes(R.color.orange))
            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            gravity = if(showMaxValue) Gravity.END else Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }
        rowTotalValues.addView(tvTotal)

        // Valor total maximo posible de obtener
        if(showMaxValue){
            val tvTotalMaxValue = TextView(context).apply {
                text = subStat.textTotalMaxValue()
                setTextColor(context.colorRes(R.color.green))
                textSize = 13f
                typeface = Typeface.DEFAULT_BOLD
                gravity = Gravity.START
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
            }
            rowTotalValues.addView(tvTotalMaxValue)
        }

        rowValues.addView(rowTotalValues)

        subPropertiesCard.addView(rowValues)

        // Agregar una mini línea divisoria gris entre cada fila, excepto en la última
        if (index < rune.subStats.size - 1) {
            val horizontalLine3 = UiFactory.line(
                context,
                LinearLayout.LayoutParams.MATCH_PARENT,
                4,
                dp(8), 0, dp(8), 0
            )
            subPropertiesCard.addView(horizontalLine3)
        }
    }

    mainContainer.addView(subPropertiesCard)
    //</editor-fold>

    // Agregamos el contenedor al panel
    panel.addView(mainContainer)

    return panel
}
