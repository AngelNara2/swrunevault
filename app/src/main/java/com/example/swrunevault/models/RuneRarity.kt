package com.example.swrunevault.models

enum class RuneRarity(
    val displayName: String,
    val color: Long,
    val colorText: Long
) {
    NORMAL(
        displayName = "Normal",
        color = 0xFF564D49,
        colorText = 0xFFFFF9F5
    ),

    MAGIC(
        displayName = "Magic",
        color = 0xFF1F3F06,
        colorText = 0xFFAFC969
    ),

    RARE(
        displayName = "Rare",
        color = 0xFF1B4154,
        colorText = 0xFFAEF5FB
    ),

    HERO(
        displayName = "Hero",
        color = 0xFF661E42,
        colorText = 0xFFAAF1F9
    ),

    LEGENDARY(
        displayName = "Legend",
        color = 0xFF743214,
        colorText = 0xFFFFD0EC
    ),

    UNKNOWN(
        displayName = "Unknown",
        color = 0xFF000000,
        colorText = 0xFFFFFFFF
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