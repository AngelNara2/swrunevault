package com.example.swrunevault.views.scanoverlay

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.example.swrunevault.R
import com.example.swrunevault.controls.UiFactory
import com.example.swrunevault.extensions.colorRes
import com.example.swrunevault.models.Rune
import com.example.swrunevault.utils.getStars

@SuppressLint("SetTextI18n")
fun createScanOverlayLeftPanel(
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
        setPadding(8, 8, 4, 8)
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
    /*val imageRune = UiFactory.icon(
        context,
        context.colorRes(R.color.background_primary),
        rune.runeSet.idRuneResource,
        180,180,
        )

    headerContainer.addView(imageRune)*/
    //</editor-fold>

    //<editor-fold desc="Cabecera de la runa">
    val infoContainer = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        weightSum = 4f // Para dividir el espacio en 4
        setPadding(dp(8), 0, 0, 0)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
    }

    // Cantidad de estrellas
    val tvStarts = TextView(context).apply {
        text = ""
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

    mainContainer.addView(headerContainer)
    //</editor-fold>

    //<editor-fold desc="Datos de escaneo y ubicación">
    val middleContainer = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(0, dp(8), 0, 0) }
    }

    // Fila: Fecha Escaneo
    val rowFecha = LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL }
    rowFecha.addView(
        TextView(context).apply {
            text = "Fecha";
            setTextColor(Color.WHITE);
            layoutParams =
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f)
        }
    )
    rowFecha.addView(
        TextView(context).apply {
            text = rune.scanDate();
            setTextColor(Color.WHITE )
        }
    )
    middleContainer.addView(rowFecha)

    // Línea divisoria
    val horizontalLine1 = UiFactory.line(
        context,
        LinearLayout.LayoutParams.MATCH_PARENT,
        4,
        context.colorRes(R.color.border),
        0, dp(8), 0, dp(8)
    )
    middleContainer.addView(horizontalLine1)

    // Fila: Fecha Escaneo
    val rowHora = LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL }
    rowHora.addView(
        TextView(context).apply {
            text = "Hora";
            setTextColor(Color.WHITE);
            layoutParams =
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
        }
    )
    rowHora.addView(
        TextView(context).apply {
            text = rune.scanTime();
            setTextColor(Color.WHITE )
        }
    )
    middleContainer.addView(rowHora)

    // Línea divisoria
    val divisor2 = android.view.View(context).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 4).apply {
            setMargins(0, dp(8), 0, dp(8)) }
        setBackgroundColor(context.colorRes(R.color.border))
    }
    middleContainer.addView(divisor2)

    // Fila: Ubicación
    val rowUbicacion = LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL }
    rowUbicacion.addView(
        TextView(context).apply {
            text = "Ubicación"; setTextColor(Color.WHITE);
            layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }
    )
    rowUbicacion.addView(
        TextView(context).apply {
            text = "Inventario"; setTextColor( Color.WHITE)
        }
    )
    middleContainer.addView(rowUbicacion)

    mainContainer.addView(middleContainer)
    //</editor-fold>

    //<editor-fold desc="Selector de Estrellas">
    val selectorContainer = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(8), dp(8), dp(8), dp(8))
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(0, dp(8), 0, 0) }
        background = GradientDrawable().apply {
            setColor(context.colorRes(R.color.background_primary))
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 16f // Esquinas redondeadas en píxeles
        }
    }

    val tvEstrellasLabel = TextView(context).apply {
        text = "Estrellas"
        setTextColor(Color.WHITE)
        typeface = Typeface.DEFAULT_BOLD
    }
    selectorContainer.addView(tvEstrellasLabel)

    // Fila de botones de estrellas
    val rowBotones = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(0, dp(8), 0, 0) }
    }

    // Texto informativo final de cálculos
    val tvCalculoInfo = TextView(context).apply {
        text = "(Los cálculos se realizan como 6★)"
        gravity = Gravity.CENTER
        textSize = 12f
        setTextColor(Color.WHITE)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(0, 16, 0, 0) }
    }

    // Lista para controlar los estados visuales de los botones
    val listaBotones = ArrayList<Button>()

    // Función interna para actualizar laUI de las estrellas superiores de forma dinámica
    fun actualizarEstrellasUI(cantidad: Int) {
        tvStarts.text = getStars(cantidad)

        tvCalculoInfo.text = "(Los cálculos se realizan como $cantidad★)"

        // Cambiar estados de los botones (el seleccionado se vuelve morado)
        listaBotones.forEachIndexed { index, button ->
            button.background = GradientDrawable().apply {
                setColor(
                    if(index + 1 == cantidad) context.colorRes(R.color.orange) else context.colorRes(R.color.background_primary)
                )
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 16f // Esquinas redondeadas en píxeles
            }

            button.setTextColor(
                if(index + 1 == cantidad) Color.BLACK else Color.WHITE
            )
        }
    }

    // Crear dinámicamente los 6 botones
    for (i in 1..6) {
        val btn = Button(context, null, android.R.attr.button).apply {
            text = "${i}★"
            gravity = Gravity.CENTER
            textSize = 12f
            layoutParams = LinearLayout.LayoutParams(
                0,
                50,
                1f).
            apply {
                setMargins(4, 4, 4, 4)
            }
            setOnClickListener {
                actualizarEstrellasUI(i)
            }
        }
        listaBotones.add(btn)
        rowBotones.addView(btn)
    }

    selectorContainer.addView(rowBotones)
    mainContainer.addView(selectorContainer)
    mainContainer.addView(tvCalculoInfo)

    // Estado inicial por defecto: 6 Estrellas
    actualizarEstrellasUI(6)
    //</editor-fold>

    // Agregamos el contenedor al panel
    panel.addView(mainContainer)

    return panel
}