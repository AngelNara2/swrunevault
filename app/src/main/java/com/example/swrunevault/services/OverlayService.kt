package com.example.swrunevault.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import com.example.swrunevault.data.SettingsManager
import com.example.swrunevault.managers.BitmapCropManager
import com.example.swrunevault.managers.NotificationOverlayManager
import com.example.swrunevault.managers.OverlayManager
import com.example.swrunevault.managers.RegexManager
import com.example.swrunevault.managers.RuneRegexManager
import com.example.swrunevault.managers.ScanOverlayManager
import com.example.swrunevault.managers.ScreenCaptureManager
import com.example.swrunevault.managers.ScreenshotOverlayManager
import com.example.swrunevault.managers.TextRecognitionManager
import com.example.swrunevault.models.RuneSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OverlayService : Service() {
    companion object {
        // Acción utilizada para detener el servicio desde la notificación.
        const val ACTION_STOP = "ACTION_STOP"
    }

    // Manager encargado de crear la notificación foreground.
    private lateinit var notificationManager: NotificationOverlayManager

    // Manager encargado del overlay flotante.
    private lateinit var overlayManager: OverlayManager

    // Manager encargado de la captura de pantalla mediante MediaProjection.
    private lateinit var screenCaptureManager: ScreenCaptureManager

    // Manager encargado de mostrar la captura fullscreen.
    private lateinit var screenshotOverlayManager: ScreenshotOverlayManager

    // Manager encargado de recortar la captura de pantalla obtenida de MediaProjection.
    private lateinit var bitmapCropManager: BitmapCropManager

    // Manager encargado de reconocer el texto usando Google ML Kit Text Recognition
    private lateinit var textRecognitionManager: TextRecognitionManager

    private lateinit var settingsManager: SettingsManager

    private lateinit var runeRegexManager: RuneRegexManager

    private lateinit var scanOverlayManager: ScanOverlayManager

    // Este servicio no utiliza binding, por lo tanto retornamos null.
    override fun onBind(
        intent: Intent?
    ): IBinder? {
        return null
    }

    // Se ejecuta una sola vez al iniciar el servicio.
    override fun onCreate() {
        super.onCreate()

        // Inicializamos managers.
        notificationManager = NotificationOverlayManager(this)

        overlayManager = OverlayManager(this)

        screenCaptureManager = ScreenCaptureManager(this)

        screenshotOverlayManager = ScreenshotOverlayManager(this)

        bitmapCropManager = BitmapCropManager()

        textRecognitionManager = TextRecognitionManager()

        settingsManager = SettingsManager(this)

        scanOverlayManager = ScanOverlayManager(this)

        CoroutineScope(Dispatchers.Main).launch {
            val language =
                settingsManager.getLanguage()

            Log.d(
                "SETTINDS MANAGER LANGUAGE",
                "Lenguaje: ${language?.code} - ${language?.displayName}"
            )

            val regexManager =
                RegexManager(language)

            runeRegexManager =
                RuneRegexManager(
                    regexManager.getProvider()
                )
        }

        // Iniciamos la notificación foreground.
        // Android requiere esto para permitir el uso de MediaProjection.
        notificationManager .createNotification()
    }

    // Se ejecuta cada vez que el servicio recibe un Intent.
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        // Detener el servicio si se recibe la acción ACTION_STOP.
        if (intent?.action == ACTION_STOP) {
            stopSelf()
        }

        // Resultado del permiso de captura de pantalla.
        val resultCode =
            intent?.getIntExtra(
                "resultCode",
                0
            )

        // Intent devuelto por MediaProjection.
        @Suppress("DEPRECATION")
        val data =
            intent?.getParcelableExtra<Intent>(
                "data"
            )

        // Validamos que existan los datos necesarios.
        if (
            resultCode != null &&
            data != null
        ) {
            // Inicializamos MediaProjection.
            screenCaptureManager
                .setupMediaProjection(
                    resultCode,
                    data
                )

            // Creamos el overlay flotante.
            overlayManager
                .createOverlay {
                    // Ocultamos el overlay para evitar que aparezca dentro de la captura.
                    overlayManager .hideOverlay()
                    Handler(mainLooper).postDelayed({
                        // Realizamos captura de pantalla.
                        screenCaptureManager.capture { bitmap ->
                            // Recortamos esquina superior derecha.
                            val croppedBitmap = bitmapCropManager.cropTopRight(bitmap)

                            // Reconocemos el texto dentro del bitmap
                            textRecognitionManager.recognizeText(croppedBitmap) { groupedLines ->
                                //Aplicamos regex para analizar el texto
                                runeRegexManager.analyze(groupedLines){ rune ->

                                    if(rune.runeSet != RuneSet.UNKNOWN){
                                        Log.d("OCR_FLOW", "Rune creada: $rune")

                                        scanOverlayManager.show(
                                            rune,
                                            onClose = {
                                                overlayManager.showOverlay()
                                            }
                                        )
                                    }else{
                                        overlayManager.showOverlay()
                                    }
                                }
                            }

                            // Mostramos la captura fullscreen.
                            /*screenshotOverlayManager.show(croppedBitmap) {
                                // Al cerrar la captura, volvemos a mostrar el overlay flotante.
                                overlayManager.showOverlay()
                            }*/
                        }
                    }, 150)
                }
        }

        // Mantiene vivo el servicio si Android necesita recrearlo.
        return START_STICKY
    }

    // Se ejecuta cuando la app es removida de recientes.
    override fun onTaskRemoved(
        rootIntent: Intent?
    ) {
        super.onTaskRemoved(
            rootIntent
        )
        stopSelf()
    }

    // Limpieza final del servicio.
    override fun onDestroy() {
        super.onDestroy()
        // Eliminamos overlay flotante.
        overlayManager.removeOverlay()

        // Eliminamos overlay fullscreen.
        screenshotOverlayManager .remove()
    }
}