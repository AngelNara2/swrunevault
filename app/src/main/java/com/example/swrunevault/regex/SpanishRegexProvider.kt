package com.example.swrunevault.regex

class SpanishRegexProvider :
    RegexProvider {

    override fun runeHeader(): Regex {
        return Regex(
            """^.*?(?<level>\+\d+)?Runa(?:(?<innate>Strong|Tenacious|Ferocious|Powerful|Sturdy|Durable|Quick|Mortal|Cruel|Resistant|Intricate))?(?<set>Energy|Fatal|Blade|Swift|Focus|Guard|Endure|Shield|Revenge|Will|Nemesis|Vampire|Destroy|Despair|Violent|Rage|Fight|Determination|Enhance|Accuracy|Tolerance|Seal|Intangible)\((?<slot>\d+)\)$"""
        )
    }

    override fun runeStat(): Regex {
        return Regex(
            """^(?<anycharacter>.*)(?<stat>ATQ|DEF|HP|VEL|Tasa|Daño|RES|Precisión).+?\+?(?<value>\d+)(?<percentage>%?)(?:\+(?<grindstone>\d+)(?<percentage2>%?))?(?<enchanted>(C)?(\()?(\<)?(\))?(\>)?)?$""",
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