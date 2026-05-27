package com.example.swrunevault.managers

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.Image
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Handler
import android.util.DisplayMetrics
import android.view.WindowManager

class ScreenCaptureManager(
    private val context: Context
) {

    // MediaProjection permite capturar el contenido de la pantalla.
    // Requiere autorización explícita del usuario mediante el diálogo de captura de pantalla de Android.
    private var mediaProjection: MediaProjection? = null

    // ImageReader recibirá las imágenes capturadas desde la pantalla.
    private lateinit var imageReader: ImageReader

    // VirtualDisplay crea una pantalla virtual que renderiza el contenido de la pantalla hacia el ImageReader.
    private var virtualDisplay: VirtualDisplay? = null

    // Inicializa MediaProjection utilizando los datos autorizados por el usuario.
    fun setupMediaProjection(
        resultCode: Int,
        data: Intent
    ) {
        val mediaProjectionManager =
            context.getSystemService(
                Context.MEDIA_PROJECTION_SERVICE
            ) as MediaProjectionManager

        mediaProjection =
            mediaProjectionManager
                .getMediaProjection(
                    resultCode,
                    data
                )
    }

    // Realiza una captura de pantalla.
    // El callback onCaptured devuelve el Bitmap resultante.
    @SuppressLint("UseKtx")
    fun capture(
        onCaptured: (Bitmap) -> Unit
    ) {
        val windowManager =
            context.getSystemService(
                Context.WINDOW_SERVICE
            ) as WindowManager

        val metrics = DisplayMetrics()

        // Obtenemos la resolución actual de la pantalla.
        @Suppress("DEPRECATION")

        windowManager.defaultDisplay .getMetrics(metrics)

        val width = metrics.widthPixels

        val height = metrics.heightPixels

        // Densidad de pantalla necesaria para crear el VirtualDisplay.
        val density = metrics.densityDpi

        // Creamos el ImageReader donde se recibirá la imagen capturada.
        imageReader =
            ImageReader.newInstance(
                width,
                height,
                PixelFormat.RGBA_8888,
                2
            )

        // Creamos una pantalla virtual conectada al ImageReader.
        virtualDisplay =
            mediaProjection
                ?.createVirtualDisplay(
                    "ScreenCapture",
                    width,
                    height,
                    density,
                    // Permite reflejar la pantalla principal.
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                    // Surface donde se renderiza la captura.
                    imageReader.surface,

                    null,
                    null
                )

        // Listener que se ejecuta cuando existe una imagen disponible.
        imageReader
            .setOnImageAvailableListener({
                // Obtenemos la imagen más reciente.
                val image: Image? = imageReader.acquireLatestImage()

                if (image != null) {
                    // Información interna de los pixeles capturados.
                    val planes = image.planes

                    val buffer = planes[0].buffer

                    val pixelStride = planes[0].pixelStride

                    val rowStride = planes[0].rowStride

                    // Padding adicional que Android agrega por alineación de memoria.
                    val rowPadding = rowStride - pixelStride * width

                    // Creamos el Bitmap final.
                    val bitmap =
                        Bitmap.createBitmap(
                            width +
                                    rowPadding / pixelStride,
                            height,
                            Bitmap.Config.ARGB_8888
                        )

                    // Copiamos los pixeles capturados al Bitmap.
                    bitmap.copyPixelsFromBuffer(
                        buffer
                    )

                    // Cerramos la imagen para liberar memoria.
                    image.close()

                    // Detenemos el listener para evitar múltiples capturas en loop.
                    imageReader
                        .setOnImageAvailableListener(
                            null,
                            null
                        )

                    // Liberamos el VirtualDisplay.
                    virtualDisplay?.release()

                    virtualDisplay = null

                    // Ejecutamos el callback en el hilo principal.
                    Handler(
                        context.mainLooper
                    ).post {
                        onCaptured(bitmap)
                    }
                }

            }, null)
    }
}