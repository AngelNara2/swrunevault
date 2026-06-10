package com.example.swrunevault.models

// Runa encontrada en el juego.
data class Rune(
    // Conjunto de la runa.
    var runeSet: RuneSet = RuneSet.UNKNOWN,

    // Slot de la runa.
    var slot: Int = 0,

    // Cantidad de estrellas.
    var stars: RuneGrade = RuneGrade.SIX,

    var rarity: RuneRarity = RuneRarity.UNKNOWN,

    // Nivel de la runa.
    var level: Int = 0,

    // Propiedad principal.
    var mainStat: RuneStat? = null,

    // Propiedad innata.
    var innateStat: RuneInnateStat? = null,

    // Propiedades secundarias.
    var subStats: MutableList<RuneStat> = mutableListOf()
){
    fun titleName(): String{
        return (if(level == 0) "" else "+ $level ") +
                "${innateStat?.title} " +
                (if(runeSet == RuneSet.UNKNOWN) "" else "$runeSet ") +
                "(${slot})"
    }

    fun primaryStat(): String{
        return "${mainStat?.statType?.displayText} " +
                "+${mainStat?.value}" +
                if(mainStat?.statType?.isPercentage==true) "%" else ""
    }

    fun primaryStatMaxValue(): Double? {
        val maxValue = RuneMainStats
            .getByStatType(
                mainStat?.statType
            )?.getMaxValue(
                stars
            )

        return maxValue
    }

    fun innateStat(): String{
        if(innateStat != RuneInnateStat.UNKNOWN){
            return "${innateStat?.runeStat?.statType?.displayText} " +
                    "${innateStat?.runeStat?.value}" +
                    if (innateStat?.statType?.isPercentage == true) "%" else ""
        }
        return ""
    }

    fun innateStatMaxValue(): Int{
        return innateStat?.maxValue ?: 0
    }

    fun innateContribution(): Double{
        val innateStatValue = innateStat?.runeStat?.value
        val innateStatValueMax = innateStat?.maxValue

        val contribution: Double = (innateStatValue?.toDouble() ?: 0.0) / (innateStatValueMax?.toDouble() ?: 0.0)

        return "%.3f".format(contribution).toDouble()
    }

    fun subStatContributionTotal(): Double{
        var totalContribution: Double = 0.0

        for (stat in subStats){
            totalContribution += stat.subStatContribution(stat.subStatMaxValue(stars).toDouble())
        }

        if(innateStat?.statType != RuneStatType.UNKNOWN){
            val innateStatValue = innateStat?.runeStat?.value
            val innateStatValueMax = innateStat?.maxValue

            val innateContribution: Double = (innateStatValue?.toDouble() ?: 0.0) / (innateStatValueMax?.toDouble() ?: 0.0)

            totalContribution += innateContribution
        }

        return "%.3f".format(totalContribution).toDouble()
    }

    fun currentEfficiency(): Double {
        var efficiency = 0.0

        var theoreticalMaximum = 2.8

        if(innateStat?.statType != RuneStatType.UNKNOWN){
            theoreticalMaximum = 3.0
        }

        efficiency = (subStatContributionTotal() / theoreticalMaximum) * 100

        return "%.2f".format(efficiency).toDouble()
    }
}