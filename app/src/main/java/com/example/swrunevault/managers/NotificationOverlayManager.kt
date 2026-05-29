package com.example.swrunevault.managers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.swrunevault.MainActivity
import com.example.swrunevault.R
import com.example.swrunevault.services.OverlayService

class NotificationOverlayManager(
    private val context: Context
) {
    // Crea y muestra la notificación persistente necesaria para mantener vivo el OverlayService.
    // Android requiere que los Foreground Services muestren una notificación visible al usuario.
    fun createNotification() {
        // Creamos el canal de notificación.
        // Los NotificationChannel son obligatorios desde Android 8 (API 26).
        val channel = NotificationChannel(
            "overlay_channel",
            "Overlay Service",
            NotificationManager.IMPORTANCE_LOW
        )

        val manager = context.getSystemService(
            NotificationManager::class.java
        )

        manager.createNotificationChannel(channel)

        // Intent para abrir la aplicación al tocar la notificación.
        val openAppIntent = Intent(
            context,
            MainActivity::class.java
        )

        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                openAppIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

        // Intent para detener el servicio desde el botón "Detener" dentro de la notificación.
        val stopIntent = Intent(
            context,
            OverlayService::class.java
        ).apply {
            action = OverlayService.ACTION_STOP
        }

        val stopPendingIntent =
            PendingIntent.getService(
                context,
                1,
                stopIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

        // Construcción de la notificación.
        val notification =
            NotificationCompat.Builder(
                context,
                "overlay_channel"
            )
                .setSmallIcon(
                    R.drawable.ic_launcher_foreground
                )

                .setContentTitle(
                    "SWRuneVault"
                )

                .setContentText(
                    "Overlay activo"
                )

                .setContentIntent(
                    pendingIntent
                )

                // Botón para detener el overlay.
                .addAction(
                    0,
                    "Detener",
                    stopPendingIntent
                )

                // Evita que el usuario pueda deslizar la notificación.
                .setOngoing(true)

                .build()

        // Inicia el servicio en modo foreground.
        // Esto evita que Android cierre el servicio automáticamente.
        (context as OverlayService)
            .startForeground(
                1,
                notification
            )
    }
}