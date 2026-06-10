package com.example.swrunevault.models

// Base de conocimiento de todas las propiedades secundarias.
object RuneSubStats {
    // Atajo para crear rangos.
    private fun r(
        min: Int,
        max: Int
    ): SubStatRange {
        return SubStatRange(
            min,
            max
        )
    }

    // Atajo para crear el mapa de estrellas.
    private fun createRanges(
        one: SubStatRange,
        two: SubStatRange,
        three: SubStatRange,
        four: SubStatRange,
        five: SubStatRange,
        six: SubStatRange
    ): Map<RuneGrade, SubStatRange> {
        return mapOf(
            RuneGrade.ONE to one,
            RuneGrade.TWO to two,
            RuneGrade.THREE to three,
            RuneGrade.FOUR to four,
            RuneGrade.FIVE to five,
            RuneGrade.SIX to six
        )
    }

    val UNKNOWN =
        RuneSubStatDefinition(
            statType =
                RuneStatType.UNKNOWN,
            ranges =
                createRanges(
                    r(0, 0),
                    r(0, 0),
                    r(0, 0),
                    r(0, 0),
                    r(0, 0),
                    r(0, 0)
                )
        )

    // HP
    val HP =
        RuneSubStatDefinition(
            statType =
                RuneStatType.HP,
            ranges =
                createRanges(
                    r(15, 60),
                    r(30, 105),
                    r(45, 165),
                    r(60, 225),
                    r(90, 300),
                    r(135, 375)
                )
        )

    // HP %
    val HP_PERCENT =
        RuneSubStatDefinition(
            statType =
                RuneStatType.HP_PERCENT,
            ranges =
                createRanges(
                    r(1, 2),
                    r(1, 3),
                    r(2, 5),
                    r(3, 6),
                    r(4, 7),
                    r(5, 8)
                )
        )

    // ATK
    val ATK =
        RuneSubStatDefinition(
            statType =
                RuneStatType.ATK,
            ranges =
                createRanges(
                    r(1, 4),
                    r(2, 5),
                    r(3, 8),
                    r(4, 10),
                    r(8, 15),
                    r(10, 20)
                )
        )

    // ATK %
    val ATK_PERCENT =
        RuneSubStatDefinition(
            statType =
                RuneStatType.ATK_PERCENT,
            ranges =
                createRanges(
                    r(1, 2),
                    r(1, 3),
                    r(2, 5),
                    r(3, 6),
                    r(4, 7),
                    r(5, 8)
                )
        )

    // DEF
    val DEF =
        RuneSubStatDefinition(
            statType =
                RuneStatType.DEF,
            ranges =
                createRanges(
                    r(1, 4),
                    r(2, 5),
                    r(3, 8),
                    r(4, 10),
                    r(8, 15),
                    r(10, 20)
                )
        )

    // DEF %
    val DEF_PERCENT =
        RuneSubStatDefinition(
            statType =
                RuneStatType.DEF_PERCENT,
            ranges =
                createRanges(
                    r(1, 2),
                    r(1, 3),
                    r(2, 5),
                    r(3, 6),
                    r(4, 7),
                    r(5, 8)
                )
        )

    // Velocidad
    val SPD =
        RuneSubStatDefinition(
            statType =
                RuneStatType.SPD,
            ranges =
                createRanges(
                    r(1, 1),
                    r(1, 2),
                    r(1, 3),
                    r(2, 4),
                    r(3, 5),
                    r(4, 6)
                )
        )

    // Tasa Crítica %
    val CRIT_RATE =
        RuneSubStatDefinition(
            statType =
                RuneStatType.CRIT_RATE,
            ranges =
                createRanges(
                    r(1, 2),
                    r(1, 3),
                    r(1, 3),
                    r(2, 4),
                    r(3, 5),
                    r(4, 6)
                )
        )

    // Daño Crítico %
    val CRIT_DAMAGE =
        RuneSubStatDefinition(
            statType =
                RuneStatType.CRIT_DAMAGE,
            ranges =
                createRanges(
                    r(1, 2),
                    r(1, 3),
                    r(2, 4),
                    r(2, 5),
                    r(3, 5),
                    r(4, 7)
                )
        )

    // Resistencia %
    val RESISTANCE =
        RuneSubStatDefinition(
            statType =
                RuneStatType.RESISTANCE,
            ranges =
                createRanges(
                    r(1, 2),
                    r(1, 3),
                    r(2, 4),
                    r(2, 5),
                    r(3, 7),
                    r(4, 8)
                )
        )

    // Precisión %
    val ACCURACY =
        RuneSubStatDefinition(
            statType =
                RuneStatType.ACCURACY,
            ranges =
                createRanges(
                    r(1, 2),
                    r(1, 3),
                    r(2, 4),
                    r(2, 5),
                    r(3, 7),
                    r(4, 8)
                )
        )

    private val allStats = listOf(
        UNKNOWN,
        HP_PERCENT,
        HP,
        ATK_PERCENT,
        ATK,
        DEF_PERCENT,
        DEF,
        SPD,
        CRIT_RATE,
        CRIT_DAMAGE,
        RESISTANCE,
        ACCURACY
    )

    fun getByStatType(
        statType: RuneStatType?
    ): RuneSubStatDefinition? {
        return allStats.firstOrNull {
            it.statType == statType
        }
    }
}