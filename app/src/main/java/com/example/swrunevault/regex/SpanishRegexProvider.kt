package com.example.swrunevault.regex

class SpanishRegexProvider :
    RegexProvider {

    override fun runeHeader(): Regex {
        return Regex(
            """\+(\d+)\s+Runa\s+(?:(\w+)\s+)?(\w+)\s+\((\d)\)"""
        )
    }

    override fun runeStat(): Regex {
        return Regex(
            """^(ATQ|DEF|HP|VEL|Tasa CRİ|Tasa CRÍ|Daño CRİ|Daño CRÍ|RES|Precisión)\s*\+(\d+)(%)?.*$""",
            RegexOption.IGNORE_CASE
        )
    }

    override fun runeRarity(): Regex {
        return Regex(
            """\b(Legend|Hero|Magic|Rare|Normal)\b""",
            RegexOption.IGNORE_CASE
        )
    }
}