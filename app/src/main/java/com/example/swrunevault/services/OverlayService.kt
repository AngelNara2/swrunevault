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
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.example.swrunevault.MainActivity
import com.example.swrunevault.R

class OverlayService : Service() {
    companion object {
        const val ACTION_STOP =
            "ACTION_STOP"
    }

    private lateinit var windowManager: WindowManager

    private lateinit var floatingView: ImageView

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
            .setContentText(
                "Overlay activo"
            )
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

        floatingView = ImageView(this)

        floatingView.setImageResource(
            R.drawable.rune_violent
        )

        floatingView.setOnClickListener {
            floatingView.alpha = 0f
            showScreenshotWindow()
        }

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

        floatingView.setOnTouchListener(
            object : View.OnTouchListener {
                private var initialX = 0
                private var initialY = 0
                private var initialTouchX = 0f
                private var initialTouchY = 0f

                override fun onTouch(
                    v: View?,
                    event: MotionEvent
                ): Boolean {
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            v?.performClick()
                            initialX = params.x
                            initialY = params.y
                            initialTouchX = event.rawX
                            initialTouchY = event.rawY
                            return true
                        }
                        MotionEvent.ACTION_MOVE -> {
                            params.x =
                                initialX + (event.rawX - initialTouchX).toInt()
                            params.y =
                                initialY + (event.rawY - initialTouchY).toInt()
                            windowManager.updateViewLayout(
                                floatingView,
                                params
                            )
                            return true
                        }
                    }
                    return false
                }
            }
        )
        if (floatingView.parent == null) {
            windowManager.addView(
                floatingView,
                params
            )
        }
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)

        stopSelf()
    }

    private fun showScreenshotWindow() {
        screenshotView = FrameLayout(this)

        screenshotView.setBackgroundColor(
            android.graphics.Color.BLACK
        )

        val closeButton = TextView(this)

        closeButton.text = "✕"

        closeButton.textSize = 30f

        closeButton.setTextColor(Color.WHITE)

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

        screenshotView.addView(closeButton)

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

    override fun onDestroy() {
        super.onDestroy()
        if (::floatingView.isInitialized) {
            windowManager.removeView(
                floatingView
            )
        }
    }
}