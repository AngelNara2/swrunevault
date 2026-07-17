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

    fun textValueStat(): String{
       return "${value}${if(statType.isPercentage) "%" else ""}"
    }

    fun hasGrindstone(): Boolean{
        return statType.hasGrindsTone
    }

    fun hasMaxGrindstoneValue(): Boolean{
        return grindstonevalue == statType.grindstoneMaxValue
    }

    fun textGrindstoneValue(): String{
        if(!statType.hasGrindsTone) return "-"

        return "${(if(grindstonevalue == 0) (statType.grindstoneMaxValue) else grindstonevalue)}${if(statType.isPercentage) "%" else ""}"
    }

    fun textGrindstoneMaxValue(): String{
        if(!statType.hasGrindsTone) return "-"

        return " (${statType.grindstoneMaxValue}${if (statType.isPercentage) "%" else ""})"
    }

    fun textTotalValue(): String{
        return "${value+grindstonevalue}${if(statType.isPercentage) "%" else ""}"
    }

    fun textTotalMaxValue(): String{
        return " (${value+statType.grindstoneMaxValue}${if(statType.isPercentage) "%" else ""})"
    }

    fun secondaryStat(): String{
        return "${statType.displayText} " +
                "+${value}" +
                (if (statType.isPercentage) "%" else "") +
                if(grindstonevalue != 0)
                    "+${grindstonevalue}" + (if (statType.isPercentage) "%" else "")
                else ""
    }

    fun subStatMaxValue(): Double{
        if(stars == RuneGrade.ZERO) {throw RuneNotFoundException("No se asigno el grado de estrellas de la runa")}

        val maxValue = RuneSubStats
            .getByStatType(
                statType
            )?.getMaxValue(
                stars
            )

        return (maxValue?.times(5)?:0).toDouble()
    }

    fun subStatGrindStoneValue(): Double{
        return (value + grindstonevalue).toDouble()
    }

    fun subStatMaxIncrementValue(): Double{
        if(stars == RuneGrade.ZERO) {throw RuneNotFoundException("No se asigno el grado de estrellas de la runa")}

        val maxValue = RuneSubStats
            .getByStatType(
                statType
            )?.getMaxValue(
                stars
            )

        return ((maxValue?.times(5)?:0) + (statType.grindstoneMaxValue)).toDouble()
    }

    fun subStatCurrentContribution(): Double{
        if(stars == RuneGrade.ZERO) {throw RuneNotFoundException("No se asigno el grado de estrellas de la runa")}

        val contribution =  value / subStatMaxValue()

        return "%.3f".format(contribution).toDouble()
    }

    fun subStatGrindStoneContribution(): Double{
        if(stars == RuneGrade.ZERO) {throw RuneNotFoundException("No se asigno el grado de estrellas de la runa")}

        val contribution: Double = (subStatGrindStoneValue()) / (subStatMaxIncrementValue() + grindstonevalue)
        return "%.3f".format(contribution).toDouble()
    }

    fun subStatMaxContribution(): Double{
        if(stars == RuneGrade.ZERO) {throw RuneNotFoundException("No se asigno el grado de estrellas de la runa")}

        val value = value.toDouble()
        val increment = statType.grindstoneMaxValue.toDouble()
        val contribution: Double = (value + increment) / (subStatMaxIncrementValue())
        return "%.3f".format(contribution).toDouble()
    }

    fun getColorByValueGrinstone(): Int {
        if ((grindstonevalue == 0) and (statType.hasGrindsTone)) return R.color.gray // No tiene implementada una Grindstone

        val minValue = statType.grindstoneMinValue?: 0
        val maxValue = statType.grindstoneMaxValue?: 0

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