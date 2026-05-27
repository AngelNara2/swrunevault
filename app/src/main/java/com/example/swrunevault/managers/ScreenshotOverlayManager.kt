package com.example.swrunevault.managers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

class ScreenshotOverlayManager(
    private val context: Context
) {
    // WindowManager permite mostrar vistas flotantes por encima de otras aplicaciones.
    private val windowManager =
        context.getSystemService(
            Context.WINDOW_SERVICE
        ) as WindowManager

    // Vista principal que contendrá la captura de pantalla y los controles.
    private var screenshotView: FrameLayout? = null

    // Muestra la captura de pantalla en un overlay fullscreen.
    // onClose se ejecuta al cerrar la vista de screenshot.
    fun show(
        bitmap: Bitmap,
        onClose: () -> Unit
    ) {
        // Eliminamos cualquier overlay previo para evitar duplicados.
        remove()

        // Contenedor principal fullscreen.
        screenshotView = FrameLayout(context)

        // Fondo negro detrás de la captura.
        screenshotView
            ?.setBackgroundColor(
                Color.BLACK
            )

        // Vista donde se mostrará el bitmap capturado.
        val imageView = ImageView(context)

        imageView.setImageBitmap(bitmap)

        // Ajusta la imagen dentro del contenedor manteniendo la proporción.
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER

        // Agregamos la imagen al contenedor principal.
        screenshotView?.addView(
            imageView
        )

        // Botón para cerrar el overlay fullscreen.
        val closeButton = TextView(context)

        closeButton.text = "✕"

        closeButton.textSize = 30f

        closeButton.setTextColor(
            Color.WHITE
        )

        // Fondo semitransparente para mejorar visibilidad.
        closeButton.setBackgroundColor(
            Color.parseColor("#66000000")
        )

        // Espaciado interno del botón.
        closeButton.setPadding(
            30,
            20,
            30,
            20
        )

        // Posición del botón.
        val closeParams =
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )

        // Mostramos el botón en la esquina superior izquierda.
        closeParams.gravity = Gravity.TOP or Gravity.START

        closeButton.layoutParams = closeParams

        // Evento click del botón cerrar.
        closeButton.setOnClickListener {
            // Eliminamos el overlay fullscreen.
            remove()
            // Ejecutamos callback externo.
            onClose()
        }

        // Agregamos el botón cerrar al overlay.
        screenshotView?.addView(
            closeButton
        )

        // Configuración de la ventana fullscreen.
        val params =
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                // Permite mostrar la vista encima de otras aplicaciones.
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                // Flags de comportamiento.
                0,
                // Permite transparencia.
                PixelFormat.TRANSLUCENT
            )

        // Mostramos el overlay fullscreen.
        windowManager.addView(
            screenshotView,
            params
        )
    }

    // Elimina el overlay fullscreen si existe actualmente.
    fun remove() {
        screenshotView?.let {
            // Verificamos que la vista siga conectada al WindowManager.
            if (it.parent != null) {
                windowManager.removeView(it)
            }
        }

        // Liberamos referencia.
        screenshotView = null
    }
}