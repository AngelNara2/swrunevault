package com.example.swrunevault.models

import androidx.compose.ui.graphics.Color

enum class RuneRarity(
    val displayName: String,
    val color: Color,
    val colorText: Color
) {
    NORMAL(
        displayName = "Normal",
        color = Color(0xFF564D49),
        colorText = Color(0xFFFFF9F5)
    ),

    MAGIC(
        displayName = "Magic",
        color = Color(0xFF1b3a07),
        colorText = Color(0xFFb4cd6f)
    ),

    RARE(
        displayName = "Rare",
        color = Color(0xFF1a3c4b),
        colorText = Color(0xFFa3cedb)
    ),

    HERO(
        displayName = "Hero",
        color = Color(0xFF5e1c3d),
        colorText = Color(0xFFe2acca)
    ),

    LEGENDARY(
        displayName = "Legend",
        color = Color(0xFF743214),
        colorText = Color(0xFFe4a97a)
    ),

    UNKNOWN(
        displayName = "Unknown",
        color = Color(0xFF000000),
        colorText = Color(0xFFFFFFFF)
    );

    companion object {
        fun fromText(text: String): RuneRarity? {
            return entries.firstOrNull {
                it.displayName.equals(
                    text.trim(),
                    ignoreCase = true
                )
            }
        }
    }
}