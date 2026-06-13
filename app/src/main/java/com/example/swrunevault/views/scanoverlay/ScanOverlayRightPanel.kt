package com.example.swrunevault.views.scanoverlay

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.example.swrunevault.models.Rune

fun createScanOverlayRightPanel(
    context: Context,
    rune: Rune,
    onClose: () -> Unit,
    onRemove: () -> Unit
): FrameLayout {

    val panel =
        FrameLayout(context).apply {

            setBackgroundColor(
                Color.BLUE
            )

            layoutParams =
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
        }

    val closeButton =
        Button(context).apply {

            text = "Cerrar"

            setOnClickListener {

                onRemove()

                onClose()
            }
        }

    panel.addView(
        closeButton,
        FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {

            gravity =
                Gravity.TOP or Gravity.END

            topMargin = 16

            marginEnd = 16
        }
    )

    return panel
}
