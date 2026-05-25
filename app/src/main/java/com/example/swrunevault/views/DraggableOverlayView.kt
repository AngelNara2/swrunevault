package com.example.swrunevault.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.FrameLayout
import kotlin.math.abs

class DraggableOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    lateinit var windowManager: WindowManager
    lateinit var layoutParams: WindowManager.LayoutParams
    var onClickAction: (() -> Unit)? = null
    private var initialX = 0
    private var initialY = 0
    private var initialTouchX = 0f
    private var initialTouchY = 0f
    private var isDragging = false
    override fun performClick(): Boolean {
        super.performClick()
        onClickAction?.invoke()
        return true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = layoutParams.x
                initialY = layoutParams.y

                initialTouchX = event.rawX
                initialTouchY = event.rawY

                isDragging = false

                return true
            }

            MotionEvent.ACTION_MOVE -> {
                val deltaX =
                    (event.rawX - initialTouchX).toInt()

                val deltaY =
                    (event.rawY - initialTouchY).toInt()

                if (
                    abs(deltaX) > 10 ||
                    abs(deltaY) > 10
                ) {
                    isDragging = true
                }

                layoutParams.x = initialX + deltaX

                layoutParams.y = initialY + deltaY

                windowManager.updateViewLayout(
                    this,
                    layoutParams
                )
                return true
            }

            MotionEvent.ACTION_UP -> {
                if (!isDragging) {
                    performClick()
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }
}