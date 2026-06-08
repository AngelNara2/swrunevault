package com.example.swrunevault.managers

import android.util.Log
import com.example.swrunevault.models.Rune
import com.example.swrunevault.models.RuneInnateStat
import com.example.swrunevault.models.RuneRarity
import com.example.swrunevault.models.RuneSet
import com.example.swrunevault.models.RuneStat
import com.example.swrunevault.models.RuneStatType
import com.example.swrunevault.regex.RegexProvider

class RuneRegexManager(
    private val regexProvider: RegexProvider
) {
    private lateinit var rune: Rune

    fun analyze(
        groupedLines:
        MutableList<
                MutableList<com.google.mlkit.vision.text.Text.Line>
                >
    ) {
        val headerRegex = regexProvider.runeHeader()
        val statRegex = regexProvider.runeStat()
        val rarityregex = regexProvider.runeRarity()

        rune = Rune()

        var matchHeader: MatchResult? = null

        var matchRarity: MatchResult? = null

        val stats = mutableListOf<RuneStat>()

        for (group in groupedLines) {
            // Ordenar fila de izquierda a derecha.
            val sortedRow =
                group.sortedBy {
                    it.boundingBox?.left ?: 0
                }

            //Log.d("OCR","====================")

            //Mostrar elementos ya ordenados.
            for (line in sortedRow) {
                val text = line.text
                val box = line.boundingBox
                val x = box?.left ?: 0
                val y = box?.top ?: 0

                //Log.d("OCR","Texto: $text | X:$x Y:$y")

                matchHeader = headerRegex.find(text)

                // Nivel - Propiedad Innate - Set - Slot
                if (matchHeader != null) {
                    /*Log.d("RUNE_REGEX","Nivel: ${matchHeader.groupValues[1]}")
                    Log.d("RUNE_REGEX","Innate: ${matchHeader.groupValues[2]}")
                    Log.d("RUNE_REGEX","Set: ${matchHeader.groupValues[3]}")
                    Log.d("RUNE_REGEX","Slot: ${matchHeader.groupValues[4]}")*/

                    rune.level = matchHeader.groupValues[1].toInt()
                    rune.innateStat = RuneInnateStat.fromText(matchHeader.groupValues[2]) ?: RuneInnateStat.UNKNOWN
                    rune.runeSet = RuneSet.fromText(matchHeader.groupValues[3]) ?: RuneSet.UNKNOWN
                    rune.slot = matchHeader.groupValues[4].toInt()
                }

                matchRarity = rarityregex.find(text)

                if (matchRarity != null){
                    //Log.d("RUNE_REGEX","Rareza: ${matchRarity.groupValues[1]}")
                    rune.rarity = RuneRarity.fromText(matchRarity.groupValues[1]) ?: RuneRarity.UNKNOWN
                }

                val matchStat = statRegex.find(text)

                if (matchStat != null)
                {
                    /*Log.d("RUNE_REGEX","Tipo: ${matchStat.groupValues[1]}")
                    Log.d("RUNE_REGEX","Valor: ${matchStat.groupValues[2]}")
                    Log.d("RUNE_REGEX","Porcentual: ${matchStat.groupValues[3] == "%"}")*/

                    stats.add(
                        RuneStat(
                            RuneStatType.fromText(
                                matchStat.groupValues[1],
                                matchStat.groupValues[3] == "%"),
                            matchStat.groupValues[2].toInt()
                        )
                    )
                }
            }
        }

        rune.mainStat = stats[0]

        if(rune.innateStat == RuneInnateStat.UNKNOWN){
            rune.subStats.add(stats[1])
            rune.subStats.add(stats[2])
            rune.subStats.add(stats[3])
            rune.subStats.add(stats[4])
        }
        else
        {
            rune.innateStat?.runeStat = stats[1]

            rune.subStats.add(stats[2])
            rune.subStats.add(stats[3])
            rune.subStats.add(stats[4])
            rune.subStats.add(stats[5])
        }

        Log.d("RUNE_CREATE","====================")

        Log.d("RUNE_CREATE",rune.titleName())
        Log.d("RUNE_CREATE",rune.primaryStat())

        if(rune.innateStat != RuneInnateStat.UNKNOWN){
            Log.d("RUNE_CREATE",rune.innateStat())
        }

        for (stat in rune.subStats){
            Log.d("RUNE_CREATE",stat.secondaryStat())
        }
    }
}