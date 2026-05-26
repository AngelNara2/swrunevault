package com.example.swrunevault.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

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