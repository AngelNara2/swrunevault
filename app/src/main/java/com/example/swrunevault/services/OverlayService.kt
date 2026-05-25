package com.example.swrunevault.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.example.swrunevault.MainActivity
import com.example.swrunevault.R
import com.example.swrunevault.views.DraggableOverlayView

class OverlayService : Service() {

    companion object {
        const val ACTION_STOP =
            "ACTION_STOP"
    }

    private lateinit var windowManager: WindowManager

    private lateinit var floatingView: DraggableOverlayView

    private lateinit var screenshotView: FrameLayout

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotification()
        createOverlay()
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        if (intent?.action == ACTION_STOP) {
            stopSelf()
        }

        return START_STICKY
    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "overlay_channel",
                "Overlay Service",
                NotificationManager.IMPORTANCE_LOW
            )

            val manager = getSystemService(
                NotificationManager::class.java
            )

            manager.createNotificationChannel(channel)
        }

        val openAppIntent = Intent(
            this,
            MainActivity::class.java
        )

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            openAppIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val stopIntent = Intent(
            this,
            OverlayService::class.java
        ).apply {
            action = ACTION_STOP
        }

        val stopPendingIntent = PendingIntent.getService(
            this,
            1,
            stopIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(
            this,
            "overlay_channel"
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("SWRuneVault")
            .setContentText("Overlay activo")
            .setContentIntent(pendingIntent)
            .addAction(
                0,
                "Detener",
                stopPendingIntent
            )
            .setOngoing(true)
            .build()
        startForeground(
            1,
            notification
        )
    }

    private fun createOverlay() {
        windowManager = getSystemService(
            WINDOW_SERVICE
        ) as WindowManager

        floatingView = DraggableOverlayView(this)

        val imageView = ImageView(this)

        imageView.setImageResource(
            R.drawable.rune_violent
        )

        imageView.layoutParams =
            FrameLayout.LayoutParams(
                150,
                150
            )

        floatingView.addView(imageView)

        val params = WindowManager.LayoutParams(
            150,
            150,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.TOP or Gravity.START

        params.x = 100
        params.y = 300

        floatingView.windowManager = windowManager

        floatingView.layoutParams = params

        floatingView.onClickAction = {
            floatingView.alpha = 0f
            showScreenshotWindow()
        }

        if (floatingView.parent == null) {
            windowManager.addView(
                floatingView,
                params
            )
        }
    }

    private fun showScreenshotWindow() {
        screenshotView = FrameLayout(this)

        screenshotView.setBackgroundColor(
            Color.parseColor("#CC000000")
        )

        val closeButton = TextView(this)

        closeButton.text = "✕"

        closeButton.textSize = 30f

        closeButton.setTextColor(
            Color.WHITE
        )

        closeButton.setPadding(
            50,
            50,
            50,
            50
        )

        closeButton.setOnClickListener {
            if (::screenshotView.isInitialized) {
                windowManager.removeView(
                    screenshotView
                )
            }

            floatingView.alpha = 1f
        }

        screenshotView.addView(
            closeButton
        )

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        windowManager.addView(
            screenshotView,
            params
        )
    }

    override fun onTaskRemoved(
        rootIntent: Intent?
    ) {
        super.onTaskRemoved(
            rootIntent
        )

        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (::floatingView.isInitialized) {
            windowManager.removeView(
                floatingView
            )
        }

        if (::screenshotView.isInitialized) {
            windowManager.removeView(
                screenshotView
            )
        }
    }
}