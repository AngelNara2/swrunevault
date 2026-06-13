package com.example.swrunevault.views.scanoverlay

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.swrunevault.models.Rune
import com.example.swrunevault.utils.getStars
import androidx.core.graphics.toColorInt

@SuppressLint("SetTextI18n")
fun createScanOverlayLeftPanel(
    context: Context,
    rune: Rune // Aquí puedes extraer datos reales de tu modelo en lugar de los strings hardcodeados
): FrameLayout {

    // 1. Panel Principal (El contenedor que regresas)
    val panel = FrameLayout(context).apply {
        setBackgroundColor(Color.WHITE) // Cambiado de RED a WHITE para el fondo del panel
        layoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT,
            1f
        )
        setPadding(8, 8, 8, 8)
    }

    // Contenedor Vertical principal para estructurar las secciones de arriba a abajo
    val mainContainer = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(10, 10, 10, 10)
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
    // SECCIÓN SUPERIOR: Imagen e Info Principal
    // ==========================================
    val headerContainer = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
    }

    // FRAME LAYOUT para la Imagen de la Runa (permite encimar elementos)
    val imageContainer = FrameLayout(context).apply {
        layoutParams = LinearLayout.LayoutParams(150, 150) // Ajusta el tamaño según tu escala

    }

    // Imagen Base de la Runa
    val imgObjeto = ImageView(context).apply {
        layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        scaleType = ImageView.ScaleType.FIT_CENTER
        setBackgroundColor(Color.LTGRAY) // Placeholder
        setImageResource(rune.runeSet.idRuneResource) // Cuando tengas tus recursos
        setPadding(10, 10, 10, 10)
    }
    imageContainer.addView(imgObjeto)

    headerContainer.addView(imageContainer)

    // Contenedor derecho de la cabecera (Estrellas, Título, etc.)
    val infoContainer = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(8, 0, 0, 0)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    // Contenedor dinámico para las estrellas superiores
    /*val containerStarsUpper = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
    }
    infoContainer.addView(containerStarsUpper)*/

    val tvStarts = TextView(context).apply {
        text = ""
        setTextColor("#5E24B3".toColorInt())
        setPadding(0, 0, 0, 0)
        typeface = Typeface.DEFAULT_BOLD
    }
    infoContainer.addView(tvStarts)

    // Título (Rage)
    val tvTitle = TextView(context).apply {
        text = rune.runeSet.name // rune.name
        setTextColor("#5E24B3".toColorInt())
        typeface = Typeface.DEFAULT_BOLD
    }
    infoContainer.addView(tvTitle)

    // Etiqueta (Hero)
    val tvTag = TextView(context).apply {
        text = rune.rarity.name
        setTextColor(rune.rarity.colorTextString.toColorInt())
        setBackgroundColor(rune.rarity.colorString.toColorInt())
        setPadding(16, 8, 16, 8)
        typeface = Typeface.DEFAULT_BOLD
    }
    infoContainer.addView(tvTag)

    // Slot (Slot 1)
    val tvSlot = TextView(context).apply {
        text = "Slot ${rune.slot}"
        setTextColor(Color.BLACK)
        typeface = Typeface.DEFAULT_BOLD
        setPadding(0, 12, 0, 0)
    }
    infoContainer.addView(tvSlot)

    headerContainer.addView(infoContainer)
    mainContainer.addView(headerContainer)

    // ==========================================
    // SECCIÓN MEDIO: Datos de escaneo y ubicación
    // ==========================================
    val middleContainer = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(0, 8, 0, 0) }
    }

    // Fila: Fecha Escaneo
    val rowFecha = LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL }
    rowFecha.addView(TextView(context).apply { text = "Escaneo"; setTextColor(Color.GRAY); layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f) })
    rowFecha.addView(TextView(context).apply { text = "22/11/2024"; setTextColor(Color.BLACK )})
    middleContainer.addView(rowFecha)

    // Línea divisoria
    val divisor = android.view.View(context).apply {
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2).apply { setMargins(0, 24, 0, 24) }
        setBackgroundColor("#E0E0E0".toColorInt())
    }
    middleContainer.addView(divisor)

    // Fila: Ubicación
    val rowUbicacion = LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL }
    rowUbicacion.addView(TextView(context).apply { text = "Ubicación"; setTextColor(Color.GRAY); layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f) })
    rowUbicacion.addView(TextView(context).apply { text = "Inventario"; setTextColor( Color.BLACK )})
    middleContainer.addView(rowUbicacion)

    mainContainer.addView(middleContainer)

    // ==========================================
    // SECCIÓN INFERIOR: Selector de Estrellas
    // ==========================================
    val selectorContainer = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        setBackgroundColor("#F6F2FF".toColorInt())
        setPadding(24, 24, 24, 24)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(0, 40, 0, 0) }
    }

    val tvEstrellasLabel = TextView(context).apply {
        text = "Estrellas"
        setTextColor(Color.DKGRAY)
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
        ).apply { setMargins(0, 16, 0, 0) }
    }

    // Texto informativo final de cálculos
    val tvCalculoInfo = TextView(context).apply {
        text = "(Los cálculos se realizan como 6★)"
        gravity = Gravity.CENTER
        textSize = 12f
        setTextColor(Color.GRAY)
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
            if (index + 1 == cantidad) {
                button.setBackgroundColor(Color.parseColor("#5E24B3"))
                button.setTextColor(Color.WHITE)
            } else {
                button.setBackgroundColor(Color.TRANSPARENT)
                button.setTextColor(Color.BLACK)
            }
        }
    }

    // Crear dinámicamente los 6 botones
    for (i in 1..6) {
        val btn = Button(context, null, android.R.attr.buttonStyleSmall).apply {
            text = "${i}★"
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
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

    // Agregamos todo el árbol al panel final
    panel.addView(mainContainer)

    return panel
}