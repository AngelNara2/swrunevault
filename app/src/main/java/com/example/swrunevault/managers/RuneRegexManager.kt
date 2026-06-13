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
                >,
        onResult: (
            Rune
                ) -> Unit
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

            //Mostrar elementos ya ordenados.
            for (line in sortedRow) {
                val text = line.text
                val box = line.boundingBox
                val x = box?.left ?: 0
                val y = box?.top ?: 0

                Log.d("OCR","Texto: $text | X:$x Y:$y")

                matchHeader = headerRegex.find(text)

                // Nivel - Propiedad Innate - Set - Slot
                if (matchHeader != null) {
                    Log.d("RUNE_REGEX","Nivel: ${matchHeader.groupValues[1]}")
                    Log.d("RUNE_REGEX","Innate: ${matchHeader.groupValues[2]}")
                    Log.d("RUNE_REGEX","Set: ${matchHeader.groupValues[3]}")
                    Log.d("RUNE_REGEX","Slot: ${matchHeader.groupValues[4]}")

                    rune.level = matchHeader.groups["level"]?.value?.toIntOrNull() ?: 0
                    rune.innateStat = RuneInnateStat.fromText(matchHeader.groups["innate"]?.value?: "") ?: RuneInnateStat.UNKNOWN
                    rune.runeSet = RuneSet.fromText(matchHeader.groups["set"]?.value?: "")  ?: RuneSet.UNKNOWN
                    rune.slot =  matchHeader.groups["slot"]?.value?.toIntOrNull() ?: 0
                }

                matchRarity = rarityregex.find(text)

                if (matchRarity != null){
                    Log.d("RUNE_REGEX","Rareza: ${matchRarity.groupValues[1]}")

                    rune.rarity = RuneRarity.fromText(matchRarity.groups["rarity"]?.value ?: "") ?: RuneRarity.UNKNOWN
                }

                val matchStat = statRegex.find(text)

                if (matchStat != null)
                {
                    if((matchStat.groups["anycharacter"]?.value?:"") == ""){
                        Log.d("RUNE_REGEX","Tipo: ${matchStat.groups["stat"]?.value?:""}")
                        Log.d("RUNE_REGEX","Valor: ${matchStat.groups["value"]?.value?.replace(" ","")?.toIntOrNull() ?: 0}")
                        Log.d("RUNE_REGEX","Porcentual: ${(matchStat.groups["percentage"]?.value ?: "") == "%"}")
                        Log.d("RUNE_REGEX","Incremento: ${matchStat.groups["increment"]?.value?.toIntOrNull() ?: 0}")

                        stats.add(
                            RuneStat(
                                RuneStatType.fromText(
                                    matchStat.groups["stat"]?.value?:"",
                                    (matchStat.groups["percentage"]?.value ?: "") == "%"
                                ),
                                matchStat.groups["value"]?.value?.replace(" ","")?.toIntOrNull() ?: 0,
                                matchStat.groups["increment"]?.value?.toIntOrNull() ?: 0
                            )
                        )
                    }
                }
            }
        }

        rune.mainStat = stats[0]
        stats.remove(stats[0])
        if(rune.innateStat == RuneInnateStat.UNKNOWN){
            rune.subStats.add(stats[1])

            stats.remove(stats[1])
        }

        for (stat in stats)
        {
            rune.subStats.add(stat)
        }

        Log.d("RUNE_CREATE","====================")

        Log.d("RUNE_CREATE",rune.titleName())
        Log.d("RUNE_CREATE",rune.primaryStat())
        Log.d("RUNE_CREATE","Valor maximo: ${rune.primaryStatMaxValue()}")

        if(rune.innateStat != RuneInnateStat.UNKNOWN){
            Log.d("RUNE_CREATE","====================")
            Log.d("RUNE_CREATE",rune.innateStat())
            Log.d("RUNE_CREATE","Valor maximo del innate ${rune.innateStatMaxValue()}")
            Log.d("RUNE_CREATE","Contribution innate ${rune.innateContribution()}")
        }

        for (stat in rune.subStats){
            Log.d("RUNE_CREATE","====================")
            Log.d("RUNE_CREATE",stat.secondaryStat())
            Log.d("RUNE_CREATE","Valor actual maximo del subStat ${stat.subStatMaxValue(rune.stars)}")
            Log.d("RUNE_CREATE","Contribution actual subStat ${stat.subStatCurrentContribution(stat.subStatMaxValue(rune.stars).toDouble())}")
            Log.d("RUNE_CREATE","Valor maximo maximo del subStat ${stat.subStatMaxIncrementValue(rune.stars)}")
            Log.d("RUNE_CREATE","Contribution maxima subStat ${stat.subStatMaxContribution(stat.subStatMaxIncrementValue(rune.stars).toDouble())}")
        }

        Log.d("RUNE_CREATE","====================")
        Log.d("RUNE_CREATE","Contribucion total actual ${rune.subStatCurrentContributionTotal()}")
        Log.d("RUNE_CREATE","Contribucion total maxima ${rune.subStatMaxContributionTotal()}")

        Log.d("RUNE_CREATE","====================")
        Log.d("RUNE_CREATE","Eficiencia actual ${rune.currentEfficiency()}")
        Log.d("RUNE_CREATE","Eficiencia maxima ${rune.maxEfficiency()}")

        onResult(rune)
    }
}