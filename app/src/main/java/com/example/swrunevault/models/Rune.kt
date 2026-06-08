package com.example.swrunevault.models

// Runa encontrada en el juego.
data class Rune(
    // Conjunto de la runa.
    var runeSet: RuneSet = RuneSet.UNKNOWN,

    // Slot de la runa.
    var slot: Int = 0,

    // Cantidad de estrellas.
    var stars: Int = 0,

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
        return "+${level} " +
                "${innateStat?.title} " +
                "${if(runeSet == RuneSet.UNKNOWN) "" else runeSet} " +
                "(${slot})"
    }

    fun primaryStat(): String{
        return "${mainStat?.statType?.displayText} " +
                "+${mainStat?.value}" +
                if(mainStat?.statType?.isPercentage==true) "%" else ""
    }

    fun innateStat(): String{
        if(innateStat != RuneInnateStat.UNKNOWN){
            return "${innateStat?.runeStat?.statType?.displayText} " +
                    "${innateStat?.runeStat?.value}" +
                    if (innateStat?.statType?.isPercentage == true) "%" else ""
        }
        return ""
    }
}