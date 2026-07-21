package com.example.swrunevault.controls

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
enum class LineOrientation {
    HORIZONTAL,
    VERTICAL
}

class DividerView(context: Context) : View(context) {

    fun setup(
        orientation: LineOrientation,
        thickness: Int,
        color: Int = Color.BLACK,
        left: Int = 0,
        top: Int = 0,
        right: Int = 0,
        bottom: Int = 0
    ) {
        layoutParams = LinearLayout.LayoutParams(
            if (orientation == LineOrientation.HORIZONTAL)
                LinearLayout.LayoutParams.MATCH_PARENT
            else
                thickness,
            if (orientation == LineOrientation.HORIZONTAL)
                thickness
            else
                LinearLayout.LayoutParams.MATCH_PARENT
        ).apply {
            setMargins(left, top, right, bottom)
        }

        setBackgroundColor(color)
    }
}