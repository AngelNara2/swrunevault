package com.example.swrunevault.models

// Tipos posibles de stats.
enum class RuneStatType(
    // Nombre mostrado.
    val displayName: String,

    // Indica si el stat és porcentual.
    val isPercentage: Boolean,

    var displayText: String = ""
) {
    // Nombre mostrado de la propiedad en En | Es
    UNKNOWN("UNKNOWN",false),
    HP("HP",false),
    HP_PERCENT("HP",true),
    ATK("ATK|ATQ",false),
    ATK_PERCENT("ATK|ATQ",true),
    DEF("DEF",false),
    DEF_PERCENT("DEF",true),
    SPD("SPD|VEL",false),
    CRIT_RATE("CRI Rate|Tasa CRİ|Tasa CRÍ",true),
    CRIT_DAMAGE("CRI Dmg|Daño CRİ|Daño CRÍ",true),
    ACCURACY("Accuracy|Precisión",true),
    RESISTANCE("RES|Resistencia",true);

    companion object {
        // Buscar stat usando OCR.
        fun fromText(
            text: String,
            ispercentage: Boolean
        ): RuneStatType? {
            val runeStatType = entries.firstOrNull {
                it.displayName.contains(
                    text.trim(),
                    ignoreCase = true
                ) && it.isPercentage == ispercentage
            }

            runeStatType?.displayText = text.trim()

            return runeStatType
        }
    }
}