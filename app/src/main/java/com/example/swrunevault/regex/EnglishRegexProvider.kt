package com.example.swrunevault.regex

class EnglishRegexProvider :
    RegexProvider {

    override fun runeHeader(): Regex {
        return Regex(
            """(?:\+(?<level>\d+)\s+)?(?:(?<innate>\w+)\s+)?(?<set>\w+)\s+Rune\s+\((?<slot>\d)\)"""
        )
    }

    override fun runeStat(): Regex {
        return Regex(
            """^(?<stat>ATK|DEF|HP|SPD|CRI Rate|CRI Dmg|Resistance|Accuracy)\s*\+(?<value>\d+)(?<percentage>%)?(?:\+(?<increment>\d+))?.*$""",
            RegexOption.IGNORE_CASE
        )
    }

    override fun runeRarity(): Regex {
        return Regex(
            """\b(?<rarity>Legend|Hero|Magic|Rare|Normal)\b""",
            RegexOption.IGNORE_CASE
        )
    }
}