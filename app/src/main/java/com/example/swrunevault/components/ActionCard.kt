package com.example.swrunevault.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
@Composable
fun ActionCard(
    cardSize: Int = 110,
    @DrawableRes imageRes: Int,
    imageSize: Int = 60,
    title: String,
    titleBold: Boolean = true,
    subtitle: String,
    subtitleBold: Boolean = false,
    primaryColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(cardSize.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen
            Card(
                modifier = Modifier.size(imageSize.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = title,
                    modifier = Modifier
                        .fillMaxSize(),
                    colorFilter = ColorFilter.tint(primaryColor),
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            // Textos
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = if(titleBold) MaterialTheme.typography.bodyMedium else MaterialTheme.typography.labelSmall,
                    color = if(titleBold) primaryColor else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = if(titleBold) FontWeight.Bold else FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = subtitle,
                    style = if(subtitleBold) MaterialTheme.typography.bodyMedium else MaterialTheme.typography.labelSmall,
                    color = if(subtitleBold) primaryColor else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = if(subtitleBold) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}