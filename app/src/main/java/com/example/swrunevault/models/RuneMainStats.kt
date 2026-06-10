package com.example.swrunevault.models

// Base de conocimiento de todas las propiedades principales.
object RuneMainStats {

    // Atajo para crear un MainStatValue.
    private fun v(
        initial: Double,
        increment: Double,
        max: Double
    ): MainStatValue {
        return MainStatValue(
            initial,
            increment,
            max
        )
    }

    //Atajo para crear el mapa de estrellas.
    private fun createValues(
        one: MainStatValue,
        two: MainStatValue,
        three: MainStatValue,
        four: MainStatValue,
        five: MainStatValue,
        six: MainStatValue
    ): Map<RuneGrade, MainStatValue> {
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
        RuneMainStatDefinition(
            statType =
                RuneStatType.UNKNOWN,
            values =
                createValues(
                    v(0.0, 0.0, 0.0),
                    v(0.0, 0.0, 0.0),
                    v(0.0, 0.0, 0.0),
                    v(0.0, 0.0, 0.0),
                    v(0.0, 0.0, 0.0),
                    v(0.0, 0.0, 0.0)
                )
        )

    val HP_PERCENT =
        RuneMainStatDefinition(
            statType =
                RuneStatType.HP_PERCENT,
            values =
                createValues(
                    v(1.0, 1.0, 18.0),
                    v(2.0, 1.0, 19.0),
                    v(4.0, 2.0, 38.0),
                    v(5.0, 2.15, 43.0),
                    v(8.0, 2.45, 51.0),
                    v(11.0, 3.0, 63.0)
                )
        )

    val HP =
        RuneMainStatDefinition(
            statType =
                RuneStatType.HP,
            values =
                createValues(
                    v(40.0, 45.0, 804.0),
                    v(70.0, 60.0, 1092.0),
                    v(100.0, 75.0, 1380.0),
                    v(160.0, 90.0, 1704.0),
                    v(270.0, 105.0, 2088.0),
                    v(360.0, 120.0, 2448.0)
                )
        )

    val ATK_PERCENT =
        RuneMainStatDefinition(
            statType =
                RuneStatType.ATK_PERCENT,
            values =
                createValues(
                    v(1.0, 1.0, 18.0),
                    v(2.0, 1.0, 19.0),
                    v(4.0, 2.0, 38.0),
                    v(5.0, 2.15, 43.0),
                    v(8.0, 2.45, 51.0),
                    v(11.0, 3.0, 63.0)
                )
        )

    val ATK =
        RuneMainStatDefinition(
            statType =
                RuneStatType.ATK,
            values =
                createValues(
                    v(3.0, 3.0, 54.0),
                    v(5.0, 4.0, 73.0),
                    v(7.0, 5.0, 92.0),
                    v(10.0, 6.0, 112.0),
                    v(15.0, 7.0, 135.0),
                    v(22.0, 8.0, 160.0)
                )
        )

    val DEF =
        RuneMainStatDefinition(
            statType =
                RuneStatType.DEF,
            values =
                createValues(
                    v(3.0, 3.0, 54.0),
                    v(5.0, 4.0, 73.0),
                    v(7.0, 5.0, 92.0),
                    v(10.0, 6.0, 112.0),
                    v(15.0, 7.0, 135.0),
                    v(22.0, 8.0, 160.0)
                )
        )

    val DEF_PERCENT =
        RuneMainStatDefinition(
            statType =
                RuneStatType.DEF_PERCENT,
            values =
                createValues(
                    v(1.0, 1.0, 18.0),
                    v(2.0, 1.0, 19.0),
                    v(4.0, 2.0, 38.0),
                    v(5.0, 2.15, 43.0),
                    v(8.0, 2.45, 51.0),
                    v(11.0, 3.0, 63.0)
                )
        )

    val SPD =
        RuneMainStatDefinition(
            statType =
                RuneStatType.SPD,
            values =
                createValues(
                    v(1.0, 1.0, 18.0),
                    v(2.0, 1.0, 19.0),
                    v(3.0, 1.33, 25.0),
                    v(4.0, 1.5, 30.0),
                    v(5.0, 2.0, 39.0),
                    v(7.0, 2.0, 42.0)
                )
        )

    val CRIT_RATE =
        RuneMainStatDefinition(
            statType =
                RuneStatType.CRIT_RATE,
            values =
                createValues(
                    v(1.0, 1.0, 18.0),
                    v(2.0, 1.0, 19.0),
                    v(3.0, 2.0, 37.0),
                    v(4.0, 2.15, 42.0),
                    v(5.0, 2.45, 47.0),
                    v(7.0, 3.0, 58.0)
                )
        )

    val CRIT_DAMAGE =
        RuneMainStatDefinition(
            statType =
                RuneStatType.CRIT_DAMAGE,
            values =
                createValues(
                    v(2.0, 1.0, 19.0),
                    v(3.0, 2.0, 37.0),
                    v(4.0, 2.25, 43.0),
                    v(6.0, 3.0, 57.0),
                    v(8.0, 3.33, 65.0),
                    v(11.0, 4.0, 80.0)
                )
        )

    val RESISTANCE =
        RuneMainStatDefinition(
            statType =
                RuneStatType.RESISTANCE,
            values =
                createValues(
                    v(1.0, 1.0, 18.0),
                    v(2.0, 1.0, 19.0),
                    v(4.0, 2.0, 38.0),
                    v(6.0, 2.15, 44.0),
                    v(9.0, 2.45, 51.0),
                    v(12.0, 3.0, 64.0)
                )
        )

    val ACCURACY =
        RuneMainStatDefinition(
            statType =
                RuneStatType.ACCURACY,
            values =
                createValues(
                    v(1.0, 1.0, 18.0),
                    v(2.0, 1.0, 19.0),
                    v(4.0, 2.0, 38.0),
                    v(6.0, 2.15, 44.0),
                    v(9.0, 2.45, 51.0),
                    v(12.0, 3.0, 64.0)
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
    ): RuneMainStatDefinition? {
        return allStats.firstOrNull {
            it.statType == statType
        }
    }
}