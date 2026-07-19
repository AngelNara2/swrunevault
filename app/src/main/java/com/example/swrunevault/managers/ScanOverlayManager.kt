package com.example.swrunevault.managers

import android.content.Context
import android.graphics.PixelFormat
import android.view.WindowManager
import android.widget.FrameLayout
import com.example.swrunevault.R
import com.example.swrunevault.extensions.colorRes
import com.example.swrunevault.models.Rune
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
                rune,
                onClose,
                ::remove
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
