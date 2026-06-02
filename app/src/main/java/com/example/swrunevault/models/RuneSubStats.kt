package com.example.swrunevault.models

// Base de conocimiento de todas las propiedades secundarias.
object RuneSubStats {
    // Atajo para crear rangos.
    private fun r(
        min: Double,
        max: Double
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

    // HP
    val HP =
        RuneSubStatDefinition(
            statType =
                RuneStatType.HP,
            ranges =
                createRanges(
                    r(15.0, 60.0),
                    r(30.0, 105.0),
                    r(45.0, 165.0),
                    r(60.0, 225.0),
                    r(90.0, 300.0),
                    r(135.0, 375.0)
                )
        )

    // HP %
    val HP_PERCENT =
        RuneSubStatDefinition(
            statType =
                RuneStatType.HP_PERCENT,
            ranges =
                createRanges(
                    r(1.0, 2.0),
                    r(1.0, 3.0),
                    r(2.0, 5.0),
                    r(3.0, 6.0),
                    r(4.0, 7.0),
                    r(5.0, 8.0)
                )
        )

    // ATK
    val ATK =
        RuneSubStatDefinition(
            statType =
                RuneStatType.ATK,
            ranges =
                createRanges(
                    r(1.0, 4.0),
                    r(2.0, 5.0),
                    r(3.0, 8.0),
                    r(4.0, 10.0),
                    r(8.0, 15.0),
                    r(10.0, 20.0)
                )
        )

    // ATK %
    val ATK_PERCENT =
        RuneSubStatDefinition(
            statType =
                RuneStatType.ATK_PERCENT,
            ranges =
                createRanges(
                    r(1.0, 2.0),
                    r(1.0, 3.0),
                    r(2.0, 5.0),
                    r(3.0, 6.0),
                    r(4.0, 7.0),
                    r(5.0, 8.0)
                )
        )

    // DEF
    val DEF =
        RuneSubStatDefinition(
            statType =
                RuneStatType.DEF,
            ranges =
                createRanges(
                    r(1.0, 4.0),
                    r(2.0, 5.0),
                    r(3.0, 8.0),
                    r(4.0, 10.0),
                    r(8.0, 15.0),
                    r(10.0, 20.0)
                )
        )

    // DEF %
    val DEF_PERCENT =
        RuneSubStatDefinition(
            statType =
                RuneStatType.DEF_PERCENT,
            ranges =
                createRanges(
                    r(1.0, 2.0),
                    r(1.0, 3.0),
                    r(2.0, 5.0),
                    r(3.0, 6.0),
                    r(4.0, 7.0),
                    r(5.0, 8.0)
                )
        )

    // Velocidad
    val SPD =
        RuneSubStatDefinition(
            statType =
                RuneStatType.SPD,
            ranges =
                createRanges(
                    r(1.0, 1.0),
                    r(1.0, 2.0),
                    r(1.0, 3.0),
                    r(2.0, 4.0),
                    r(3.0, 5.0),
                    r(4.0, 6.0)
                )
        )

    // Tasa Crítica %
    val CRIT_RATE =
        RuneSubStatDefinition(
            statType =
                RuneStatType.CRIT_RATE,
            ranges =
                createRanges(
                    r(1.0, 2.0),
                    r(1.0, 3.0),
                    r(1.0, 3.0),
                    r(2.0, 4.0),
                    r(3.0, 5.0),
                    r(4.0, 6.0)
                )
        )

    // Daño Crítico %
    val CRIT_DAMAGE =
        RuneSubStatDefinition(
            statType =
                RuneStatType.CRIT_DAMAGE,
            ranges =
                createRanges(
                    r(1.0, 2.0),
                    r(1.0, 3.0),
                    r(2.0, 4.0),
                    r(2.0, 5.0),
                    r(3.0, 5.0),
                    r(4.0, 7.0)
                )
        )

    // Resistencia %
    val RESISTANCE =
        RuneSubStatDefinition(
            statType =
                RuneStatType.RESISTANCE,
            ranges =
                createRanges(
                    r(1.0, 2.0),
                    r(1.0, 3.0),
                    r(2.0, 4.0),
                    r(2.0, 5.0),
                    r(3.0, 7.0),
                    r(4.0, 8.0)
                )
        )

    // Precisión %
    val ACCURACY =
        RuneSubStatDefinition(
            statType =
                RuneStatType.ACCURACY,
            ranges =
                createRanges(
                    r(1.0, 2.0),
                    r(1.0, 3.0),
                    r(2.0, 4.0),
                    r(2.0, 5.0),
                    r(3.0, 7.0),
                    r(4.0, 8.0)
                )
        )
}