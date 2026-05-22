package com.example.swrunevault.screens.settings.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swrunevault.R

@Preview(showBackground = true)
@Composable
fun InformationSection(){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Card(
                    modifier = Modifier.size(25.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.action_scan),
                        contentDescription = stringResource(R.string.totals),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(1.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        contentScale = ContentScale.Fit
                    )
                }
                Text(
                    text = stringResource(R.string.information),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icono de runa
                Card(
                    modifier = Modifier.size(35.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Red
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.rune_violent),
                        contentDescription = "Runa Violent",
                        modifier = Modifier.fillMaxSize().padding(5.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                // Información principal
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.app_version),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "SWRuneVault v1.0.0",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Card(
                    modifier = Modifier.size(35.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Red
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.rune_violent),
                        contentDescription = "Runa Violent",
                        modifier = Modifier.fillMaxSize().padding(5.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icono de runa
                Card(
                    modifier = Modifier.size(35.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Red
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.rune_violent),
                        contentDescription = "Runa Violent",
                        modifier = Modifier.fillMaxSize().padding(5.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                // Información principal
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.about_swrunevault),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.about_the_app),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Card(
                    modifier = Modifier.size(35.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Red
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.rune_violent),
                        contentDescription = "Runa Violent",
                        modifier = Modifier.fillMaxSize().padding(5.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}