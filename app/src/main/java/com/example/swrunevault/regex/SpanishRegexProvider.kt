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
            """^(ATQ|DEF|HP|VEL|Tasa CRÍ|Daño CRÍ|RES|Precisión)\s*\+(\d+)(%)?$""",
            RegexOption.IGNORE_CASE
        )
    }
}