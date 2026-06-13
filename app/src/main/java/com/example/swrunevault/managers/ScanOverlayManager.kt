package com.example.swrunevault.managers

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.example.swrunevault.models.Rune

class ScanOverlayManager(
    private val context: Context
) {

    private val windowManager =
        context.getSystemService(
            Context.WINDOW_SERVICE
        ) as WindowManager

    private var overlayView: FrameLayout? = null

    fun show(
        rune: Rune,
        onClose: () -> Unit
    ) {

        remove()

        // Fondo fullscreen
        overlayView =
            FrameLayout(context).apply {

                setBackgroundColor(
                    Color.parseColor("#AA000000")
                )
            }

        // Panel principal
        val panel =
            LinearLayout(context).apply {

                orientation =
                    LinearLayout.VERTICAL

                setBackgroundColor(
                    Color.WHITE
                )

                setPadding(
                    40,
                    40,
                    40,
                    40
                )
            }

        // Título
        val title =
            TextView(context).apply {

                text = "Runa detectada"

                textSize = 22f

                setTextColor(
                    Color.BLACK
                )
            }

        panel.addView(title)

        // Set
        val setText =
            TextView(context).apply {

                text =
                    "Set: ${rune.runeSet}"

                textSize = 18f
            }

        panel.addView(setText)

        // Slot
        val slotText =
            TextView(context).apply {

                text =
                    "Slot: ${rune.slot}"

                textSize = 18f
            }

        panel.addView(slotText)

        // Nivel
        val levelText =
            TextView(context).apply {

                text =
                    "Nivel: +${rune.level}"

                textSize = 18f
            }

        panel.addView(levelText)

        // Estrellas
        val starsText =
            TextView(context).apply {

                text =
                    "Estrellas: ${rune.stars}"

                textSize = 18f
            }

        panel.addView(starsText)

        // Botón cerrar
        val closeButton =
            Button(context).apply {

                text = "Cerrar"

                setOnClickListener {

                    remove()

                    onClose()
                }
            }

        panel.addView(closeButton)

        val panelParams =
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )

        panelParams.gravity =
            Gravity.CENTER

        overlayView?.addView(
            panel,
            panelParams
        )

        val params =
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
            )

        windowManager.addView(
            overlayView,
            params
        )
    }

    fun remove() {

        overlayView?.let {

            if (it.parent != null) {

                windowManager.removeView(it)
            }
        }

        overlayView = null
    }
}