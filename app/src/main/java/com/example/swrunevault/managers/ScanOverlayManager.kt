package com.example.swrunevault.managers

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.compose.ui.platform.ComposeView
import com.example.swrunevault.compose.OverlayComposeOwner
import com.example.swrunevault.models.Rune
import com.example.swrunevault.screens.scan.ScanResultOverlay
import com.example.swrunevault.ui.theme.SWRuneVaultTheme

class ScanOverlayManager(
    private val context: Context
) {

    private val windowManager =
        context.getSystemService(
            Context.WINDOW_SERVICE
        ) as WindowManager

    private var overlayView: FrameLayout? = null

    private var composeOwner: OverlayComposeOwner? = null
    fun show(
        rune: Rune,
        onClose: () -> Unit
    ) {

        remove()

        composeOwner = OverlayComposeOwner()

        overlayView =
            FrameLayout(context).apply {

                setBackgroundColor(
                    Color.parseColor("#CC000000")
                )

                val composeView =
                    ComposeView(context).apply {

                        setContent {

                            SWRuneVaultTheme {
                                ScanResultOverlay(
                                     rune = rune,
                                     onClose = {
                                        remove()
                                         onClose()
                                     }
                                 )

                            }
                        }
                    }

                /*val textView = TextView(context).apply {

                    text = "Overlay funcionando"

                    textSize = 24f

                    setTextColor(Color.WHITE)

                    gravity = Gravity.CENTER
                }*/

                addView(
                    composeView,
                    FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                )
            }

        composeOwner = OverlayComposeOwner()

        val owner = composeOwner!!

        ViewTreeLifecycleOwner.set(this, owner)
        ViewTreeSavedStateRegistryOwner.set(this, owner)
        ViewTreeViewModelStoreOwner.set(this, owner)

        val params =
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
            ).apply {
                gravity = Gravity.CENTER
            }

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

        composeOwner?.destroy()
        composeOwner = null

        overlayView = null
    }
}