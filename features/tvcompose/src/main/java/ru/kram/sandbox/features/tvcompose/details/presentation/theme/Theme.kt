package ru.kram.sandbox.features.tvcompose.details.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

import androidx.compose.foundation.isSystemInDarkTheme

data class Theme(
    val colors: AppColors,
    val typography: AppTypography,
)

val LocalTheme = staticCompositionLocalOf<Theme> { error("No Theme provided") }
val Typography @Composable get() = LocalTheme.current.typography
val Colors @Composable get() = LocalTheme.current.colors

@Composable
fun CardDetailsTheme(
    content: @Composable () -> Unit,
) {
    val isDarkTheme = isSystemInDarkTheme()

    val theme = if (isDarkTheme) {
        Theme(
            colors = DarkColors,
            typography = DarkTypography
        )
    } else {
        Theme(
            colors = LightColors,
            typography = LightTypography
        )
    }

    CompositionLocalProvider(LocalTheme provides theme) {
        content()
    }
}