package com.example.swrunevault.models

// Tipos posibles de stats.
enum class RuneStatType(
    // Nombre mostrado.
    var displayName: String,

    // Indica si el stat és porcentual.
    val isPercentage: Boolean,

    var displayText: String = "",

    val increment: Int,
) {
    // Nombre mostrado de la propiedad en En | Es
    UNKNOWN("UNKNOWN",false, increment = 0),
    HP("HP",false, increment = 550),
    HP_PERCENT("HP",true, increment = 10),
    ATK("ATK|ATQ",false, increment = 30),
    ATK_PERCENT("ATK|ATQ",true, increment = 10),
    DEF("DEF",false, increment = 30),
    DEF_PERCENT("DEF",true, increment = 10),
    SPD("SPD|VEL",false, increment = 5),
    CRIT_RATE(displayName = "CRI Rate|Tasa CRİ|Tasa CRÍ", isPercentage = true, increment = 0),
    CRIT_DAMAGE("CRI Dmg|Daño CRİ|Daño CRÍ",true, increment = 0),
    ACCURACY("Accuracy|Precisión",true, increment = 0),
    RESISTANCE("RES|Resistencia",true, increment = 0);

    companion object {
        // Buscar stat usando OCR.
        fun fromText(
            text: String,
            percentage: Boolean
        ): RuneStatType? {
            var runeStatType = entries.firstOrNull {
                it.displayName.contains(
                    //text.trim().split(" ")[0],
                    text.trim(),
                    ignoreCase = true
                ) && it.isPercentage == percentage
            }

            runeStatType?.displayText = text.trim()

            return runeStatType
        }
    }
}