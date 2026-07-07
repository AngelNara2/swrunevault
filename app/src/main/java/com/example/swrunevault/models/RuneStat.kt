package com.example.swrunevault.models

import com.example.swrunevault.R
import com.example.swrunevault.exceptions.RuneNotFoundException

// Estadística de una runa.
data class RuneStat(
    // Tipo de estadística.
    val statType: RuneStatType = RuneStatType.UNKNOWN,

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

    fun imgStat(): Int {
        return statType.idStatResource
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

    fun getColorByValueGrinstone(): Int {
        if ((grindstonevalue == 0) and (statType?.hasGrindsTone == true)) return R.color.gray // No tiene implementada una Grindstone

        val minValue = statType?.grindstoneMinValue?: 0
        val maxValue = statType?.grindstoneMaxValue?: 0

        if (grindstonevalue <= minValue) return R.color.red // El valor más bajo
        if (grindstonevalue >= maxValue) return R.color.green // El valor más alto

        val midpoint = (minValue + maxValue) / 2

        return when {
            grindstonevalue < midpoint -> R.color.orange // Orange
            grindstonevalue > midpoint -> R.color.yellow // Yellow
            else -> R.color.yellow             // Exactly at the midpoint
        }
    }

    companion object {
        fun defaultStat(): RuneStat {
            return RuneStat(
                RuneStatType.UNKNOWN,
                0,
                0
            )
        }
    }
}