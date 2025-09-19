package org.hanekoi.newcourseblock.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

internal fun courseCardColor(name: String): Color {
    val hash = name.hashCode()
    val r = (hash shr 16 and 0xFF)
    val g = (hash shr 8 and 0xFF)
    val b = (hash and 0xFF)
    return Color(r / 2 + 64, g / 2 + 64, b / 2 + 64, 0xFF)
}