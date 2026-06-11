package com.example.swrunevault.regex

class SpanishRegexProvider :
    RegexProvider {

    override fun runeHeader(): Regex {
        return Regex(
            """(?:\+(?<level>\d+)\s+)?Runa\s+(?:(?<innate>\w+)\s+)?(?<set>\w+)\s+\((?<slot>\d)\)"""
        )
    }

    override fun runeStat(): Regex {
        return Regex(
            """^(?<stat>ATQ|DEF|HP|VEL|Tasa.*|Daño.*|RES|Precisión)\s*\+(?<value>\d?\s*\d)(?<percentage>%)?(?:\+(?<increment>\d+))?.*$""",
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