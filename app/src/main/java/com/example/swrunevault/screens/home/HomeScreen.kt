package com.example.swrunevault.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swrunevault.screens.home.sections.RecentRunesSection
import com.example.swrunevault.screens.home.sections.QuickActionsSection
import com.example.swrunevault.screens.home.sections.TotalsSection

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RecentRunesSection()

        QuickActionsSection()

        TotalsSection()
    }
}