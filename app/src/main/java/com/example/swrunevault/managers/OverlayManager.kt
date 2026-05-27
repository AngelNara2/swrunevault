package com.example.swrunevault.managers

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.swrunevault.R
import com.example.swrunevault.views.DraggableOverlayView

class OverlayManager(
    private val context: Context
) {

    // WindowManager permite agregar vistas flotantes por encima de otras aplicaciones.
    private val windowManager =
        context.getSystemService(
            Context.WINDOW_SERVICE
        ) as WindowManager

    // Vista principal del overlay flotante.
    // Utilizamos un custom view llamado DraggableOverlayView para permitir mover el overlay por la pantalla.
    private var floatingView: DraggableOverlayView? = null

    // Crea el overlay flotante.
    // El parámetro onClickAction se ejecutará cuando el usuario toque el overlay.
    fun createOverlay(
        onClickAction: () -> Unit
    ) {

        // Evita crear múltiples overlays si ya existe uno activo.
        if (floatingView != null) {
            return
        }

        // Creamos el contenedor flotante.
        floatingView = DraggableOverlayView(context)

        // Imagen principal del overlay.
        val imageView =ImageView(context)

        imageView.setImageResource(
            R.drawable.rune_violent
        )

        //Tamaño de la imagen.
        imageView.layoutParams =
            FrameLayout.LayoutParams(
                150,
                150
            )

        // Agregamos la imagen al overlay flotante.
        floatingView?.addView(imageView)

        // Configuración de la ventana flotante.
        val params =
            WindowManager.LayoutParams(
                150,
                150,
                // Permite mostrar la vista encima de otras apps.
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,

                // Evita que el overlay robe el foco del sistema.
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,

                // Permite transparencia.
                PixelFormat.TRANSLUCENT
            )

        // Posición inicial del overlay.
        params.gravity = Gravity.TOP or Gravity.START

        params.x = 100
        params.y = 300

        // Referencia al WindowManager utilizada internamente por DraggableOverlayView.
        floatingView?.windowManager = windowManager

        floatingView?.layoutParams = params

        // Evento click del overlay.
        floatingView?.onClickAction = {
            onClickAction()
        }

        // Mostramos el overlay en pantalla.
        windowManager.addView(
            floatingView,
            params
        )
    }

    // Oculta temporalmente el overlay.
    // Esto se utiliza antes de tomar la captura de pantalla para evitar que el overlay aparezca dentro e la imagen capturada.
    fun hideOverlay() {
        floatingView?.visibility = View.GONE
    }

    // Vuelve a mostrar el overlay.
    fun showOverlay() {
        floatingView?.visibility =
            View.VISIBLE
    }

    // Elimina completamente el overlay.
    // Esto se utiliza normalmente cuando el servicio finaliza.
    fun removeOverlay() {
        floatingView?.let {
            // Verificamos que la vista siga conectada al WindowManager antes de removerla.
            if (it.parent != null) {
                windowManager.removeView(it)
            }
        }
        // Liberamos la referencia.
        floatingView = null
    }
}