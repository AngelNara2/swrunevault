package com.example.swrunevault.managers

import android.graphics.Bitmap

class BitmapCropManager {
    fun cropTopRight(
        bitmap: Bitmap
    ): Bitmap {
        return Bitmap.createBitmap(
            bitmap,
            //X inicial
            bitmap.width / 2,
            //Y inicial
            0,
            //Ancho
            bitmap.width / 2,
            //Alto
            bitmap.height / 2
        )
    }
}