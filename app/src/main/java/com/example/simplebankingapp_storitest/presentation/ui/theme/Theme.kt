package com.example.simplebankingapp_storitest.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun SimpleBankingAppTheme(content: @Composable () -> Unit) {
    val darkTheme = isSystemInDarkTheme()
    val colorScheme = if (!darkTheme) LightColorScheme else DarkColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

private val LightColorScheme = lightColorScheme(
    surface = Blue,
    onSurface = Color.Black,
    primary = Navy,
    onPrimary = Navy
)

private val DarkColorScheme = darkColorScheme(
    surface = Blue,
    onSurface = Color.White,
    primary = LightBlue,
    onPrimary = Chartreuse
)