package com.example.swrunevault.screens.home.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.swrunevault.R
import com.example.swrunevault.components.ActionCard
import android.content.Intent
import android.provider.Settings
import androidx.compose.ui.platform.LocalContext
import com.example.swrunevault.services.OverlayService
import androidx.core.content.ContextCompat
import com.example.swrunevault.utils.findActivity

@Composable
fun QuickActionsSection() {
    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ActionCard(
            modifier = Modifier.weight(1f),
            imageRes = R.drawable.action_scan,
            title = stringResource(R.string.scan),
            subtitle = stringResource(R.string.scan_your_runes),
            primaryColor = Color(0xFF542FA2),
            backgroundColor = Color(0xFFF3EFFD),
            onClick = {
                if (!Settings.canDrawOverlays(context)) {
                    val intent = Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION
                    )
                    context.startActivity(intent)
                }
                else {
                    val intent = Intent(
                        context,
                        OverlayService::class.java
                    )
                    ContextCompat.startForegroundService(
                        context,
                        intent
                    )
                    context.findActivity()?.moveTaskToBack(true)
                }
            }
        )
        ActionCard(
            modifier = Modifier.weight(1f),
            imageRes = R.drawable.action_inventory,
            title = stringResource(R.string.inventory),
            subtitle = stringResource(R.string.view_all_your_runes),
            primaryColor = Color(0xFF1D57AF),
            backgroundColor = Color(0xFFEBF3FE),
            onClick = {
            }
        )
    }
}