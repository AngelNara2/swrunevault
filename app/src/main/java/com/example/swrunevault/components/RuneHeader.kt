package com.example.swrunevault.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.swrunevault.models.Rune
import com.example.swrunevault.utils.getStars

@Composable
fun RuneHeader(
    rune: Rune,
    scanTime: String,
    location: String,
){
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Card(
            modifier = Modifier.size(125.dp).padding(horizontal = 10.dp).padding(top= 10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray
            )
        ) {
            Image(
                painter = painterResource(rune.runeSet.idRuneResource),
                contentDescription = rune.runeSet.name,
                modifier = Modifier.fillMaxSize().padding(10.dp),
                contentScale = ContentScale.Fit
            )
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = getStars(rune.stars.stars),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${rune.level} ${rune.runeSet.name}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF542FA2)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                color =  Color.LightGray,
                shape = RoundedCornerShape(10)
            ) {
                Text(
                    text = rune.rarity.displayName,
                    style = MaterialTheme.typography.bodyLarge,
                    color =  Color.LightGray,
                    modifier = Modifier.padding(
                        horizontal = 10.dp,
                        vertical = 4.dp
                    )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Slot ${rune.slot}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
    Column(
        modifier = Modifier.padding(
            horizontal = 10.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Fecha escaneo",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Surface(
                color = Color.Transparent
            ) {
                Text(
                    text = scanTime,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 6.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ubicación",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Surface(
                color = Color.Transparent
            ) {
                Text(
                    text = location,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 6.dp)
        )
    }
}