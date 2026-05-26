package com.example.swrunevault.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.Image
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Handler
import android.os.IBinder
import android.util.DisplayMetrics
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

    private var mediaProjection: MediaProjection? = null

    private lateinit var imageReader: ImageReader

    private var virtualDisplay: VirtualDisplay? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotification()
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        if (intent?.action == ACTION_STOP) {
            stopSelf()
        }

        val resultCode = intent?.getIntExtra(
            "resultCode",
            0
        )

        @Suppress("DEPRECATION")
        val data = intent?.getParcelableExtra<Intent>(
            "data"
        )

        if (
            resultCode != null &&
            data != null
        ) {
            val mediaProjectionManager =
                getSystemService(
                    MEDIA_PROJECTION_SERVICE
                ) as MediaProjectionManager

            mediaProjection =
                mediaProjectionManager.getMediaProjection(
                    resultCode,
                    data
                )

            if (!::floatingView.isInitialized) {
                createOverlay()
            }
        }
        return START_STICKY
    }

    private fun createNotification() {
        val channel = NotificationChannel(
            "overlay_channel",
            "Overlay Service",
            NotificationManager.IMPORTANCE_LOW
        )

        val manager = getSystemService(
            NotificationManager::class.java
        )

        manager.createNotificationChannel(channel)

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
            createScreenCapture()
        }

        if (floatingView.parent == null) {
            windowManager.addView(
                floatingView,
                params
            )
        }
    }

    private fun createScreenCapture() {
        val metrics = DisplayMetrics()

        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(
            metrics
        )

        val width = metrics.widthPixels

        val height = metrics.heightPixels

        val density = metrics.densityDpi

        imageReader = ImageReader.newInstance(
            width,
            height,
            PixelFormat.RGBA_8888,
            2
        )

        virtualDisplay = mediaProjection
            ?.createVirtualDisplay(
                "ScreenCapture",
                width,
                height,
                density,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                imageReader.surface,
                null,
                null
            )

        imageReader.setOnImageAvailableListener({
            val image: Image? =
                imageReader.acquireLatestImage()
            if (image != null) {
                val planes = image.planes

                val buffer = planes[0].buffer

                val pixelStride = planes[0].pixelStride

                val rowStride = planes[0].rowStride

                val rowPadding = rowStride - pixelStride * width

                val bitmap = Bitmap.createBitmap(
                    width + rowPadding / pixelStride,
                    height,
                    Bitmap.Config.ARGB_8888
                )

                bitmap.copyPixelsFromBuffer(
                    buffer
                )

                image.close()

                // =========================
                // DETENER LOOP
                // =========================

                imageReader.setOnImageAvailableListener(
                    null,
                    null
                )

                virtualDisplay?.release()

                virtualDisplay = null

                Handler(mainLooper).post {
                    showCapturedBitmap(bitmap)
                }
            }

        }, null)
    }

    private fun showCapturedBitmap(
        bitmap: Bitmap
    ) {
        screenshotView = FrameLayout(this)

        screenshotView.setBackgroundColor(
            Color.BLACK
        )

        val imageView = ImageView(this)

        imageView.setImageBitmap(bitmap)

        imageView.scaleType =
            ImageView.ScaleType.FIT_CENTER

        screenshotView.addView(imageView)

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
            windowManager.removeView(
                screenshotView
            )

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