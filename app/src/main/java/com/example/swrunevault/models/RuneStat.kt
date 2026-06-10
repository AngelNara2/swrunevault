package com.example.swrunevault.models

// Estadística de una runa.
data class RuneStat(
    // Tipo de estadística.
    val statType: RuneStatType? = RuneStatType.UNKNOWN,

    // Valor actual.
    val value: Int,

    // Valor incrementado
    val increment: Int
){
    fun secondaryStat(): String{
        return "${statType?.displayText} " +
                "+${value}" +
                (
                        if (statType?.isPercentage== true)
                            "%"
                        else
                            ""
                        ) +
                if(increment != 0)
                    "+${increment}" + (
                            if (statType?.isPercentage== true)
                                "%"
                            else
                                ""
                            )
                else ""
    }

    fun subStatMaxValue(stars: RuneGrade): Int{
        val maxValue = RuneSubStats
            .getByStatType(
                statType
            )?.getMaxValue(
                stars
            )

        return maxValue?.times(5)?:0
    }

    fun subStatContribution(maxValue: Double): Double{
        val contribution =  value / maxValue

        return "%.3f".format(contribution).toDouble()
    }
}