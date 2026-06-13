package com.example.swrunevault.views.scanoverlay

import android.content.Context
import android.graphics.Color
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.example.swrunevault.models.Rune

fun createScanOverlayCenterPanel(
    context: Context,
    rune: Rune
): FrameLayout {

    return FrameLayout(context).apply {

        setBackgroundColor(
            Color.GREEN
        )

        layoutParams =
            LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f
            )
    }
}
