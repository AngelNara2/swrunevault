package com.example.swrunevault.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import android.widget.ImageView
import com.example.swrunevault.R
import android.util.Log


class OverlayService : Service() {
    private lateinit var windowManager: WindowManager

    private lateinit var floatingView: ImageView

    private var isOverlayShown = false

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        if (!isOverlayShown) {
            windowManager = getSystemService(
                WINDOW_SERVICE
            ) as WindowManager

            floatingView = ImageView(this)

            floatingView.setImageResource(
                R.drawable.rune_violent
            )

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

            windowManager.addView(
                floatingView,
                params
            )

            isOverlayShown = true
        }
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)

        if (::floatingView.isInitialized) {

            windowManager.removeView(floatingView)
        }

        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (::floatingView.isInitialized) {

            windowManager.removeView(floatingView)
        }
    }
}