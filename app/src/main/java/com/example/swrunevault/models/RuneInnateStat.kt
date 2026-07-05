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
    val maxValue: Int,

    // Valor actual de la propiedad
    var runeStat: RuneStat = RuneStat.defaultStat()
) {
    UNKNOWN(RuneStatType.UNKNOWN,"Unknown",0,0),
    STRONG(RuneStatType.HP,"Strong",52,355),
    TENACIOUS(RuneStatType.HP_PERCENT,"Tenacious",1,8),
    FEROCIOUS(RuneStatType.ATK,"Ferocious",2,16),
    POWERFUL(RuneStatType.ATK_PERCENT,"Powerful",1,8),
    STURDY(RuneStatType.DEF,"Sturdy",2,17),
    DURABLE(RuneStatType.DEF_PERCENT,"Durable",1,8),
    QUICK(RuneStatType.SPD,"Quick",1,6),
    MORTAL(RuneStatType.CRIT_RATE,"Mortal",1,6),
    CRUEL(RuneStatType.CRIT_DAMAGE,"Cruel",1,7),
    RESISTANT(RuneStatType.RESISTANCE,"Resistant",1,8),
    INTRICATE(RuneStatType.ACCURACY,"Intricate",1,8);

    companion object {
        // Buscar innate por stat.
        fun fromStatType(
            statType: RuneStatType
        ): RuneInnateStat? {
            return entries.firstOrNull {
                it.statType == statType
            }
        }

        // Buscar innate por stat.
        fun fromText(
            text: String
        ): RuneInnateStat? {
            return RuneInnateStat.entries.firstOrNull {
                it.title.equals(
                    text.trim(),
                    ignoreCase = true
                )
            }
        }
    }
}