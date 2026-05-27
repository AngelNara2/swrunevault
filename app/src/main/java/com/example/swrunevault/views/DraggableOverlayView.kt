package com.example.swrunevault.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.FrameLayout
import kotlin.math.abs

class DraggableOverlayView @JvmOverloads constructor(

    context: Context,

    attrs: AttributeSet? = null

) : FrameLayout(context, attrs) {

    // WindowManager utilizado para actualizar la posición del overlay en pantalla.
    lateinit var windowManager:
            WindowManager

    //LayoutParams de la ventana flotante.
    // Aquí se almacenan coordenadas, tamaño y configuración del overlay.
    lateinit var layoutParams: WindowManager.LayoutParams

    // Callback ejecutado cuando el usuario realiza un click sobre el overlay.
    var onClickAction: (() -> Unit)? = null

    // Posición inicial del overlay antes de comenzar el drag.
    private var initialX = 0

    private var initialY = 0

    // Posición inicial del dedo al tocar la pantalla.
    private var initialTouchX = 0f

    private var initialTouchY = 0f

    // Indica si el usuario está arrastrando el overlay o simplemente haciendo click.
    private var isDragging = false

    // Método estándar de Android para manejar clicks accesibles.
    // Aquí ejecutamos el callback personalizado del overlay.
    override fun performClick(): Boolean {
        super.performClick()

        onClickAction?.invoke()

        return true
    }

    // Maneja los eventos táctiles del overlay flotante.
    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        when (event.action) {
            // El usuario toca el overlay.
            MotionEvent.ACTION_DOWN -> {
                // Guardamos la posición actual del overlay.
                initialX = layoutParams.x

                initialY = layoutParams.y

                // Guardamos la posición inicial del dedo.
                initialTouchX = event.rawX

                initialTouchY = event.rawY

                // Reiniciamos estado drag.
                isDragging = false

                return true
            }

            // El usuario mueve el dedo.
            MotionEvent.ACTION_MOVE -> {
                // Distancia movida desde el punto inicial.
                val deltaX = (event.rawX - initialTouchX) .toInt()

                val deltaY = (event.rawY - initialTouchY) .toInt()

                // Si el movimiento supera cierto umbral, consideramos que el usuario está arrastrando.
                if (
                    abs(deltaX) > 10 ||
                    abs(deltaY) > 10
                ) {
                    isDragging = true
                }

                // Actualizamos posición del overlay.
                layoutParams.x = initialX + deltaX

                layoutParams.y = initialY + deltaY

                // Aplicamos nueva posición.
                windowManager.updateViewLayout(
                    this,
                    layoutParams
                )
                return true
            }

            // El usuario soltó el dedo.
            MotionEvent.ACTION_UP -> {
                // Si no hubo drag, lo consideramos un click.
                if (!isDragging) {
                    performClick()
                }
                return true
            }
        }

        return super.onTouchEvent(event)
    }
}