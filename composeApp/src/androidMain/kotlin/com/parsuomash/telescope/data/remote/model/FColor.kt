package com.parsuomash.telescope.data.remote.model

import androidx.compose.ui.graphics.Color

enum class FColor(private val value: String) {
    Black("black"),
    DarkGray("darkgray"),
    Gray("gray"),
    LightGray("lightgray"),
    White("white"),
    Red("red"),
    Green("green"),
    Blue("blue"),
    Yellow("yellow"),
    Cyan("cyan"),
    Magenta("magenta"),
    Transparent("transparent");

    fun toComposeColor(): Color = when (this) {
        Black -> Color.Black
        DarkGray -> Color.DarkGray
        Gray -> Color.Gray
        LightGray -> Color.LightGray
        White -> Color.White
        Red -> Color.Red
        Green -> Color.Green
        Blue -> Color.Blue
        Yellow -> Color.Yellow
        Cyan -> Color.Cyan
        Magenta -> Color.Magenta
        Transparent -> Color.Transparent
    }

    companion object {
        fun parseFrom(value: String): FColor? = FColor.entries.find { it.value == value.lowercase() }
    }
}
