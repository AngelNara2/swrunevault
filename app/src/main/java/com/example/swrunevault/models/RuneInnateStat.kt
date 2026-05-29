package com.example.swrunevault.models

// Representa las propiedades prefijas (Innate).
enum class RuneInnateStat(
    // Tipo de stat.
    val statType: RuneStatType,

    // Título del innate.
    val title: String,

    // Valor mínimo posible.
    val minValue: Int,

    // Valor máximo posible.
    val maxValue: Int
) {
    HP(RuneStatType.HP,"Strong",52,355),
    HP_PERCENT(RuneStatType.HP_PERCENT,"Tenacious",1,8),
    ATK(RuneStatType.ATK,"Ferocious",2,16),
    ATK_PERCENT(RuneStatType.ATK_PERCENT,"Powerful",1,8),
    DEF(RuneStatType.DEF,"Sturdy",2,17),
    DEF_PERCENT(RuneStatType.DEF_PERCENT,"Durable",1,8),
    SPD(RuneStatType.SPD,"Quick",1,6),
    CRIT_RATE(RuneStatType.CRIT_RATE,"Mortal",1,6),
    CRIT_DAMAGE(RuneStatType.CRIT_DAMAGE,"Cruel",1,7),
    RESISTANCE(RuneStatType.RESISTANCE,"Resistant",1,8),
    ACCURACY(RuneStatType.ACCURACY,"Intricate",1,8);

    companion object {
        // Buscar innate por stat.
        fun fromStatType(
            statType: RuneStatType
        ): RuneInnateStat? {
            return entries.firstOrNull {
                it.statType == statType
            }
        }
    }
}