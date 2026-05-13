package com.example.swrunevault.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.Image
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.swrunevault.R

@Composable
fun RuneItem(
    runeName: String,
    runeStats: String,
    runeTime: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icono de runa
        Card(
            modifier = Modifier.size(55.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Red
            )
        ) {
            Image(
                painter = painterResource(R.drawable.rune_violent),

                contentDescription = "Runa Violent",

                modifier = Modifier.fillMaxSize().padding(10.dp),

                contentScale = ContentScale.Fit
            )
        }
        // Información principal
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "★★★★★",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$runeName $runeStats",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = runeTime,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        // Badge
        Surface(
            color = Color(0xFFFFEBEE),
            shape = RoundedCornerShape(50)
        ) {
            Text(
                text = "Legendaria",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFC62828),
                modifier = Modifier.padding(
                    horizontal = 10.dp,
                    vertical = 4.dp
                )
            )
        }
    }
}