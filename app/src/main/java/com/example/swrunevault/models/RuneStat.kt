package com.example.swrunevault.models

import com.example.swrunevault.exceptions.RuneNotFoundException

// Estadística de una runa.
data class RuneStat(
    // Tipo de estadística.
    val statType: RuneStatType? = RuneStatType.UNKNOWN,

    // Valor actual.
    val value: Int,

    // Valor incrementado por el uso de una grindstone
    val grindstonevalue: Int,

    // Grado de la runa para los calculos
    private var stars: RuneGrade = RuneGrade.ZERO
){
    fun runeGrade(runeGrade: RuneGrade){
        stars = runeGrade
    }

    fun secondaryStat(): String{
        return "${statType?.displayText} " +
                "+${value}" +
                (
                        if (statType?.isPercentage== true)
                            "%"
                        else
                            ""
                        ) +
                if(grindstonevalue != 0)
                    "+${grindstonevalue}" + (
                            if (statType?.isPercentage== true)
                                "%"
                            else
                                ""
                            )
                else ""
    }

    fun subStatMaxValue(): Int{
        if(stars == RuneGrade.ZERO) {throw RuneNotFoundException("No se asigno el grado de estrellas de la runa")}

        val maxValue = RuneSubStats
            .getByStatType(
                statType
            )?.getMaxValue(
                stars
            )

        return maxValue?.times(5)?:0
    }

    fun subStatGrindStoneValue(): Int{
        return value + grindstonevalue
    }

    fun subStatMaxIncrementValue(): Int{
        if(stars == RuneGrade.ZERO) {throw RuneNotFoundException("No se asigno el grado de estrellas de la runa")}

        val maxValue = RuneSubStats
            .getByStatType(
                statType
            )?.getMaxValue(
                stars
            )

        return (maxValue?.times(5)?:0) + (statType?.grindstoneMaxValue ?: 0)
    }

    fun subStatCurrentContribution(): Double{
        if(stars == RuneGrade.ZERO) {throw RuneNotFoundException("No se asigno el grado de estrellas de la runa")}

        val contribution =  value / subStatMaxValue().toDouble()

        return "%.3f".format(contribution).toDouble()
    }

    fun subStatGrindStoneContribution(): Double{
        if(stars == RuneGrade.ZERO) {throw RuneNotFoundException("No se asigno el grado de estrellas de la runa")}

        val contribution: Double = (subStatGrindStoneValue()).toDouble() / (subStatMaxIncrementValue().toDouble() + grindstonevalue)
        return "%.3f".format(contribution).toDouble()
    }

    fun subStatMaxContribution(): Double{
        if(stars == RuneGrade.ZERO) {throw RuneNotFoundException("No se asigno el grado de estrellas de la runa")}

        val value = value
        val increment = statType?.grindstoneMaxValue ?: 0
        val contribution: Double = (value + increment).toDouble() / (subStatMaxIncrementValue().toDouble())
        return "%.3f".format(contribution).toDouble()
    }

    fun getColorByValue(): String {
        if ((grindstonevalue == 0) and (statType?.hasGrindsTone == true)) return "#A9A9A9" // Gris

        val minValue = statType?.grindstoneMinValue?: 0
        val maxValue = statType?.grindstoneMaxValue?: 0

        if (grindstonevalue <= minValue) return "#FF0000" // Red
        if (grindstonevalue >= maxValue) return "#00FF00" // Green

        val midpoint = (minValue + maxValue) / 2

        return when {
            grindstonevalue < midpoint -> "#FFA500" // Orange
            grindstonevalue > midpoint -> "#FFFF00" // Yellow
            else -> "#FFFF00"             // Exactly at the midpoint
        }
    }
}