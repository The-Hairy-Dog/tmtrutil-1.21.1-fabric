package com.thehairydog.tmtrutil.util

import net.minecraft.network.chat.TextColor

object ColourUtil {
    // Pokémon type colors using TextColor
    val typeColors = mapOf(
        "normal" to TextColor.fromRgb(0xA8A77A),
        "fire" to TextColor.fromRgb(0xEE8130),
        "water" to TextColor.fromRgb(0x6390F0),
        "electric" to TextColor.fromRgb(0xF7D02C),
        "grass" to TextColor.fromRgb(0x7AC74C),
        "ice" to TextColor.fromRgb(0x96D9D6),
        "fighting" to TextColor.fromRgb(0xC22E28),
        "poison" to TextColor.fromRgb(0xA33EA1),
        "ground" to TextColor.fromRgb(0xE2BF65),
        "flying" to TextColor.fromRgb(0xA98FF3),
        "psychic" to TextColor.fromRgb(0xF95587),
        "bug" to TextColor.fromRgb(0xA6B91A),
        "rock" to TextColor.fromRgb(0xB6A136),
        "ghost" to TextColor.fromRgb(0x735797),
        "dragon" to TextColor.fromRgb(0x6F35FC),
        "dark" to TextColor.fromRgb(0x705746),
        "steel" to TextColor.fromRgb(0xB7B7CE),
        "fairy" to TextColor.fromRgb(0xD685AD)
    )

    // Move category colors
    val categoryColors = mapOf(
        "physical" to TextColor.fromRgb(0xFF5555),
        "special" to TextColor.fromRgb(0x5555FF),
        "status" to TextColor.fromRgb(0xAAAAAA)
    )

    // Optional: power & accuracy gradient function
    fun getPowerColor(power: Int?): TextColor {
        return when (power ?: 0) {
            in 0..50 -> TextColor.fromRgb(0xFF5555)
            in 51..70 -> TextColor.fromRgb(0xFFAA00)
            in 71..80 -> TextColor.fromRgb(0xFFFF55)
            in 81..90 -> TextColor.fromRgb(0x55FF55)
            in 91..95 -> TextColor.fromRgb(0x00AAAA)
            in 96..999 -> TextColor.fromRgb(0x55FFFF)
            else -> TextColor.fromRgb(0xFFFFFF)
        }
    }

    fun getAccuracyColor(accuracy: Int?): TextColor {
        return when (accuracy ?: 0) {
            in 0..50 -> TextColor.fromRgb(0xFF5555)
            in 51..70 -> TextColor.fromRgb(0xFFAA00)
            in 71..80 -> TextColor.fromRgb(0xFFFF55)
            in 81..90 -> TextColor.fromRgb(0x55FF55)
            in 91..95 -> TextColor.fromRgb(0x00AAAA)
            in 96..100 -> TextColor.fromRgb(0x55FFFF)
            else -> TextColor.fromRgb(0xFFFFFF)
        }
    }
}
