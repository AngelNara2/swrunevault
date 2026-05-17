package com.example.swrunevault.screens.home.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.swrunevault.R
import com.example.swrunevault.components.ActionCard

@Composable
fun QuickActionsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ActionCard(
            modifier = Modifier.weight(1f),
            imageRes = R.drawable.action_scan,
            title = "Escanear",
            subtitle = "Escanea tus runas",
            primaryColor = Color(0xFF542FA2),
            backgroundColor = Color(0xFFF3EFFD),
            onClick = {
            }
        )
        ActionCard(
            modifier = Modifier.weight(1f),
            imageRes = R.drawable.action_inventory,
            title = "Inventario",
            subtitle = "Ver todas tus runas",
            primaryColor = Color(0xFF1D57AF),
            backgroundColor = Color(0xFFEBF3FE),
            onClick = {
            }
        )
    }
}