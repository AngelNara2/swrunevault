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

        var matchHeader: MatchResult?

        var matchRarity: MatchResult?

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

                Log.d("OCR","Texto: $text")

                matchHeader = headerRegex.find(text)

                // Nivel - Propiedad Innate - Set - Slot
                if (matchHeader != null) {
                    Log.d("RUNE_REGEX","Nivel: ${matchHeader.groupValues[1]}")
                    Log.d("RUNE_REGEX","Innate: ${matchHeader.groupValues[2]}")
                    Log.d("RUNE_REGEX","Set: ${matchHeader.groupValues[3]}")
                    Log.d("RUNE_REGEX","Slot: ${matchHeader.groupValues[4]}")

                    rune.level = matchHeader.groups["level"]?.value?.toIntOrNull() ?: 0

                    val innateStat = RuneInnateStat.fromText(matchHeader.groups["innate"]?.value?: "")
                    Log.d("RUNE_REGEX_INNATE","Innate ${innateStat}")

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
                        Log.d("RUNE_REGEX","Tipo: ${matchStat.groups["stat"]?.value?.trim()?:""}")
                        Log.d("RUNE_REGEX","Valor: ${matchStat.groups["value"]?.value?.replace(" ","")?.toIntOrNull() ?: 0}")
                        Log.d("RUNE_REGEX","Porcentual: ${(matchStat.groups["percentage"]?.value ?: "") == "%"}")
                        Log.d("RUNE_REGEX","Grindstone: ${matchStat.groups["grindstone"]?.value?.toIntOrNull() ?: 0}")
                        Log.d("RUNE_REGEX","Enchanted: ${(matchStat.groups["enchanted"]?.value ?: "") != ""}")

                        val runeStatType = RuneStatType.fromText(
                            matchStat.groups["stat"]?.value?:"",
                            (matchStat.groups["percentage"]?.value ?: "") == "%"
                        )

                        stats.add(
                            RuneStat(
                                runeStatType,
                                matchStat.groups["value"]?.value?.replace(" ","")?.toIntOrNull() ?: 0,
                                matchStat.groups["grindstone"]?.value?.toIntOrNull() ?: 0
                            )
                        )
                    }
                }
            }
        }

        if (stats.count() != 0){
            Log.d("RUNE_CREATE_PRICIPAL","Estadistica principal: ${stats[0]}")

            rune.mainStat = stats[0]
            stats.remove(stats[0])

            if(rune.innateStat != RuneInnateStat.UNKNOWN){
                Log.d("RUNE_CREATE_INNATE","Estadistica innate: ${stats[0]}")
                rune.innateStat?.runeStat = stats[0]

                stats.remove(stats[0])
            }

            for (stat in stats)
            {
                Log.d("RUNE_CREATE_SUBSTAT","Estadistica secundaria: ${stat}")
                rune.subStats.add(stat)
            }

            Log.d("RUNE_OBJECT","====================")

            Log.d("RUNE_OBJECT_PRINCIPAL",rune.titleName())
            Log.d("RUNE_OBJECT_PRINCIPAL",rune.primaryStat())
            Log.d("RUNE_OBJECT_PRINCIPAL","Valor maximo: ${rune.primaryStatMaxValue()}")

            if(rune.innateStat != RuneInnateStat.UNKNOWN){
                Log.d("RUNE_OBJECT_INNATE","====================")
                Log.d("RUNE_OBJECT_INNATE",rune.innateStat())
                Log.d("RUNE_OBJECT_INNATE","Valor maximo del innate ${rune.innateStatMaxValue()}")
                Log.d("RUNE_OBJECT_INNATE","Contribution innate ${rune.innateContribution()}")
            }

            for (substat in rune.subStats){
                Log.d("RUNE_OBJECT_SUBSTAT","====================")
                Log.d("RUNE_OBJECT_SUBSTAT",substat.secondaryStat())
                substat.runeGrade(rune.stars)
                Log.d("RUNE_OBJECT_SUBSTAT","Estadistica base maxima")
                Log.d("RUNE_OBJECT_SUBSTAT","SubStat ${substat.subStatMaxValue()}")
                Log.d("RUNE_OBJECT_SUBSTAT","Contribution ${substat.subStatCurrentContribution()}")

                Log.d("RUNE_OBJECT_SUBSTAT","Estadistica base maxima + Grindstone maximo")
                Log.d("RUNE_OBJECT_SUBSTAT","subStat ${substat.subStatMaxIncrementValue()}")
                Log.d("RUNE_OBJECT_SUBSTAT","Contribution ${substat.subStatMaxContribution()}")
            }

            Log.d("RUNE_OBJECT","====================")
            Log.d("RUNE_OBJECT","Contribucion total actual ${rune.subStatCurrentContributionTotal()}")
            Log.d("RUNE_OBJECT","Contribucion total maxima ${rune.subStatMaxContributionTotal()}")

            Log.d("RUNE_OBJECT","====================")
            Log.d("RUNE_OBJECT","Eficiencia actual ${rune.currentEfficiency()}")
            Log.d("RUNE_OBJECT","Eficiencia maxima ${rune.maxEfficiency()}")
        }

        onResult(rune)
    }
}