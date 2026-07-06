package com.example.swrunevault.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.CustumfindActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun Context.colorRes(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}