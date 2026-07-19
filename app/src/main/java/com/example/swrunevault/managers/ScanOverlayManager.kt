package com.example.swrunevault.managers

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.swrunevault.R
import com.example.swrunevault.controls.UiFactory
import com.example.swrunevault.extensions.colorRes
import com.example.swrunevault.models.Rune
import com.example.swrunevault.models.RuneInnateStat
import com.example.swrunevault.models.RuneStat
import com.example.swrunevault.models.RuneStatType
import com.example.swrunevault.utils.getStars
import com.example.swrunevault.views.scanoverlay.createScanOverlayContainer
import com.example.swrunevault.views.scanoverlay.createScanOverlayInformation
import com.example.swrunevault.views.scanoverlay.createScanOverlayRightPanel

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
                    context.colorRes(R.color.background_primary)
                )
            }

        val container =
            createScanOverlayContainer(
                context
            )

        container.addView(
            createScanOverlayInformation(
                context,
                rune,
                onClose,
                ::remove
            )
        )

        container.addView(
            createScanOverlayRightPanel(
                context,
                rune
            )
        )

        overlayView?.addView(
            container
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
