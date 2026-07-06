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
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.graphics.toColorInt
import com.example.swrunevault.R
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

    // ==========================================
    // TITULO: EFICIENCIA
    // ==========================================
    val titleContainer = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
    }

    val imgEficienciaIcon = ImageView(context).apply {
        setBackgroundColor("#5E24B3".toColorInt())
        layoutParams = LinearLayout.LayoutParams(dp(18), dp(18)) // Escalado a DP
    }
    titleContainer.addView(imgEficienciaIcon)

    val tvSectionTitle = TextView(context).apply {
        text = "EFICIENCIA"
        setTextColor(Color.WHITE)
        textSize = 15f // Las fuentes en Android ya se auto-escalan de forma nativa (SP)
        typeface = Typeface.DEFAULT_BOLD
        //setPadding(dp(8), 0, 0, 0)
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

    // ==========================================
    // BLOQUE: EFICIENCIA ACTUAL
    // ==========================================
    val actualCard = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        setBackgroundColor(context.colorRes(R.color.background_primary))
        //setPadding(dp(8), dp(8), dp(8), dp(8))
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    val actualHeader = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL;
        gravity = Gravity.CENTER_VERTICAL
    }
    actualHeader.addView(
        TextView(context).apply {
            text = "Eficiencia Actua.";
            setTextColor(Color.WHITE);
            textSize = 14f;
            typeface = Typeface.DEFAULT_BOLD
        }
    )
    actualCard.addView(actualHeader)

    val tvActualValue = TextView(context).apply {
        text = "${rune.currentEfficiency()}%"
        setTextColor(context.colorRes(R.color.purple))
        textSize = 24f
        typeface = Typeface.DEFAULT_BOLD
    }
    actualCard.addView(tvActualValue)

    val progressActual = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal).apply {
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp(6))
        max = 100
        progress = rune.currentEfficiency().toInt()
        progressDrawable.setColorFilter(context.colorRes(R.color.purple), android.graphics.PorterDuff.Mode.SRC_IN)
    }
    actualCard.addView(progressActual)
    mainContainer.addView(actualCard)

    // Espaciador adaptativo entre tarjetas
    mainContainer.addView(android.view.View(context).apply { layoutParams = LinearLayout.LayoutParams(1, dp(12)) })

    // ==========================================
    // BLOQUE: EFICIENCIA MÁXIMA
    // ==========================================
    val maxCard = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        setBackgroundColor(context.colorRes(R.color.background_primary))
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    val maxHeader = LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
    maxHeader.addView(TextView(context).apply { text = "Eficiencia Máxima  "; setTextColor(Color.WHITE); textSize = 14f; typeface = Typeface.DEFAULT_BOLD })
    maxCard.addView(maxHeader)

    val tvMaxValue = TextView(context).apply {
        text = "${rune.maxEfficiency()}%"
        setTextColor(context.colorRes(R.color.orange))
        textSize = 24f
        typeface = Typeface.DEFAULT_BOLD
        //setPadding(0, dp(6), 0, dp(6))
    }
    maxCard.addView(tvMaxValue)

    val progressMax = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal).apply {
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp(6))
        max = 100
        progress = rune.maxEfficiency().toInt()
        progressDrawable.setColorFilter(context.colorRes(R.color.orange), android.graphics.PorterDuff.Mode.SRC_IN)
    }
    maxCard.addView(progressMax)
    mainContainer.addView(maxCard)

    // Espaciador responsivo antes de los botones
    mainContainer.addView(android.view.View(context).apply { layoutParams = LinearLayout.LayoutParams(1, dp(24)) })

    // ==========================================
    // SECCIÓN SECTORES: BOTONES DE ACCIÓN (Fila Doble)
    // ==========================================
    val actionRow = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        weightSum = 2f
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    // --- Botón Izquierdo: Escanear Otra ---
    val btnEscanearOtra = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        setBackgroundColor(context.colorRes(R.color.light_purple))
        //setPadding(dp(8), dp(8), dp(8), dp(8))
        layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)

        // 💡 SOLUCIÓN: Forzar a que el contenedor capture el clic y no sus vistas hijas
        isClickable = true
        isFocusable = true

        setOnClickListener {
            onClose()
            onRemove()
        }
    }
    btnEscanearOtra.addView(ImageView(context).apply {
        layoutParams = LinearLayout.LayoutParams(dp(8), dp(8))
        setBackgroundColor(context.colorRes(R.color.purple))
    })
    // RESTRICCIÓN RESPONSIVA: Añadimos peso (1f) al contenedor de texto interno para evitar desbordes de palabra
    val txtEscanearContainer = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        //setPadding(dp(8), 0, 0, 0)
        layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
    }
    txtEscanearContainer.addView(
        TextView(context).apply {
            text = "Escanear Otra";
            setTextColor(context.colorRes(R.color.purple));
            typeface = Typeface.DEFAULT_BOLD;
            textSize = 13f
        }
    )
    txtEscanearContainer.addView(
        TextView(context).apply {
            text = "Analizar nueva";
            setTextColor(context.colorRes(R.color.purple));
            textSize = 10f;
            isSingleLine = true
        }
    )
    btnEscanearOtra.addView(txtEscanearContainer)
    actionRow.addView(btnEscanearOtra)

    // Espacio responsivo entre botones
    actionRow.addView(android.view.View(context).apply { layoutParams = LinearLayout.LayoutParams(dp(8), 1) })

    // --- Botón Derecho: Editar Runa ---
    val btnEditarRuna = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        setBackgroundColor(context.colorRes(R.color.light_cyan))
        //setPadding(dp(8), dp(8), dp(8), dp(8))
        layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        setOnClickListener { /* Lógica para editar manualmente */ }
    }
    btnEditarRuna.addView(ImageView(context).apply {
        layoutParams = LinearLayout.LayoutParams(dp(8), dp(8))
        setBackgroundColor(context.colorRes(R.color.cyan))
    })
    val txtEditarContainer = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        //setPadding(dp(8), 0, 0, 0)
        layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
    }
    txtEditarContainer.addView(
        TextView(context).apply {
            text = "Editar Runa";
            setTextColor(context.colorRes(R.color.cyan));
            typeface = Typeface.DEFAULT_BOLD;
            textSize = 13f
        }
    )
    txtEditarContainer.addView(
        TextView(context).apply {
            text = "Manualmente";
            setTextColor(context.colorRes(R.color.cyan));
            textSize = 10f;
            isSingleLine = true
        }
    )
    btnEditarRuna.addView(txtEditarContainer)
    actionRow.addView(btnEditarRuna)

    mainContainer.addView(actionRow)

    // Espaciador antes del botón de eliminar
    mainContainer.addView(android.view.View(context).apply { layoutParams = LinearLayout.LayoutParams(1, dp(12)) })

    // ==========================================
    // BOTÓN INFERIOR LARGO: ELIMINAR RUNA
    // ==========================================
    val btnEliminarRuna = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
        setBackgroundColor(context.colorRes(R.color.light_red))
        //setPadding(dp(12), dp(12), dp(12), dp(12))
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        setOnClickListener {
            //onRemove()
        }
    }
    btnEliminarRuna.addView(ImageView(context).apply {
        layoutParams = LinearLayout.LayoutParams(dp(22), dp(22))
        setBackgroundColor(context.colorRes(R.color.red))
    })
    val txtEliminarContainer = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        //setPadding(dp(8), 0, 0, 0)
    }
    txtEliminarContainer.addView(
        TextView(context).apply {
            text = "Eliminar Runa";
            setTextColor(context.colorRes(R.color.red));
            typeface = Typeface.DEFAULT_BOLD;
            textSize = 13f
        }
    )
    txtEliminarContainer.addView(
        TextView(context).apply {
            text = "Eliminar del inventario";
            setTextColor(context.colorRes(R.color.red));
            textSize = 10f
        }
    )
    btnEliminarRuna.addView(txtEliminarContainer)

    mainContainer.addView(btnEliminarRuna)

    // Agregamos el contenedor al panel
    panel.addView(mainContainer)

    return panel
}