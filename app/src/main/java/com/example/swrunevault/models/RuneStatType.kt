package com.example.swrunevault.models

// Tipos posibles de stats.
enum class RuneStatType(
    // Nombre mostrado.
    val displayName: String,

    // Indica si el stat és porcentual.
    val isPercentage: Boolean
) {
    UNKNOWN("UNKNOWN",false),
    HP("HP",false),
    HP_PERCENT("HP %",true),
    ATK("ATK",false),
    ATK_PERCENT("ATK %",true),
    DEF("DEF",false),
    DEF_PERCENT("DEF %",true),
    SPD("VEL",false),
    CRIT_RATE("Tasa CRI %",true),
    CRIT_DAMAGE("CRI Dmg %",true),
    ACCURACY("Precisión %",true),
    RESISTANCE("Resistencia %",true);

    companion object {
        // Buscar stat usando OCR.
        fun fromText(
            text: String
        ): RuneStatType? {
            return entries.firstOrNull {
                it.displayName.equals(
                    text.trim(),
                    ignoreCase = true
                )
            }
        }
    }
}