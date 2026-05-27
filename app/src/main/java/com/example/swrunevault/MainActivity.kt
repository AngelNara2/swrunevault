package com.example.swrunevault

import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.swrunevault.screens.MainScreen
import com.example.swrunevault.services.OverlayService
import com.example.swrunevault.ui.theme.SWRuneVaultTheme

class MainActivity : ComponentActivity() {
    private lateinit var mediaProjectionManager: MediaProjectionManager

    private var mediaProjectionResultCode = 0

    private var mediaProjectionData: Intent? = null

    private val screenCaptureLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                mediaProjectionResultCode = result.resultCode

                mediaProjectionData = result.data

                val intent = Intent(
                    this,
                    OverlayService::class.java
                )

                intent.putExtra(
                    "resultCode",
                    mediaProjectionResultCode
                )

                intent.putExtra(
                    "data",
                    mediaProjectionData
                )

                ContextCompat.startForegroundService(
                    this,
                    intent
                )

                moveTaskToBack(true)

                // Lanzar Summoners War.
                val gameIntent =
                    packageManager
                        .getLaunchIntentForPackage(
                            "com.com2us.smon.normal.freefull.google.kr.android.common"
                        )

                if (gameIntent != null) {
                    gameIntent.addFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK
                    )

                    startActivity(gameIntent)
                }
            }
        }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        mediaProjectionManager =
            getSystemService(
                MEDIA_PROJECTION_SERVICE
            ) as MediaProjectionManager

        setContent {
            SWRuneVaultTheme {
                MainScreen()
            }
        }
    }

    fun requestScreenCapture() {
        val captureIntent = mediaProjectionManager.createScreenCaptureIntent()

        screenCaptureLauncher.launch(
            captureIntent
        )
    }
}