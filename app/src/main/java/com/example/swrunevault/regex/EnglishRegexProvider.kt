package com.example.swrunevault.regex

class EnglishRegexProvider :
    RegexProvider {

    override fun runeHeader(): Regex {
        return Regex(
            """\+(\d+)\s+(?:(\w+)\s+)?(\w+)\s+Rune\s+\((\d)\)"""
        )
    }

    override fun runeStat(): Regex {
        return Regex(
            """^(ATK|DEF|HP|SPD|CRI Rate|CRI Dmg|Resistance|Accuracy)\s*\+(\d+)(%)?$""",
            RegexOption.IGNORE_CASE
        )
    }
}