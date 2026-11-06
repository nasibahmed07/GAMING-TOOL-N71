package com.n7elite.gaming.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val N7Black = Color(0xFF0B0B0F)
val N7Charcoal = Color(0xFF16161D)
val N7Red = Color(0xFFFF1744)
val N7Blue = Color(0xFF29B6F6)
val N7TextDim = Color(0xFF9A9AA8)

private val N7Colors = darkColorScheme(
    primary = N7Red,
    secondary = N7Blue,
    background = N7Black,
    surface = N7Charcoal,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color(0xFFF2F2F7),
    onSurface = Color(0xFFF2F2F7)
)

@Composable
fun N7Theme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = N7Colors, content = content)
}
