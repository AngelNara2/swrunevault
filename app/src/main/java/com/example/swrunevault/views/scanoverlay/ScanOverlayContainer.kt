package com.example.swrunevault.views.scanoverlay

import android.content.Context
import android.widget.FrameLayout
import android.widget.LinearLayout

fun createScanOverlayContainer(
    context: Context
): LinearLayout {
    return LinearLayout(context).apply {

        orientation =
            LinearLayout.HORIZONTAL

        layoutParams =
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )

        setPadding(
            20,
            20,
            20,
            20
        )
    }
}
