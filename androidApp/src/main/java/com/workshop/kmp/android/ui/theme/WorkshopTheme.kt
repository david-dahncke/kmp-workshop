package com.workshop.kmp.android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val WorkshopLightColors = lightColorScheme(
    primary = Color(0xFF5C6BC0),
    onPrimary = Color.White,
    secondary = Color(0xFF7E57C2),
    background = Color(0xFFF5F5F5),
    surface = Color.White,
)

@Composable
fun WorkshopTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = WorkshopLightColors,
        content = content,
    )
}
