package ru.kram.sandbox.features.tvcompose.details.presentation.theme

import androidx.compose.ui.graphics.Color

data class AppColors(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val background: Color,
    val surface: Color,

    val onPrimary: Color,
    val onSecondary: Color,
    val onTertiary: Color,

    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textError: Color,
    val textWarning: Color,
    val textSuccess: Color,

    val iconPrimary: Color,
    val iconSecondary: Color,
    val iconTertiary: Color,

    val buttonPrimaryBackground: Color,
    val buttonPrimaryText: Color,
    val buttonSecondaryBackground: Color,
    val buttonSecondaryText: Color,
    val buttonTertiaryBackground: Color,
    val buttonTertiaryText: Color,

    val divider: Color,
)

val LightColors = AppColors(
    primary = Color(0xFFFFA500),
    secondary = Color(0xFFFFC107),
    tertiary = Color(0xFF6200EE),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFFAFAFA),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFF000000),
    onTertiary = Color(0xFFFFFFFF),
    textPrimary = Color(0xFF212121),
    textSecondary = Color(0xFF757575),
    textTertiary = Color(0xFFBDBDBD),
    textError = Color(0xFFD32F2F),
    textWarning = Color(0xFFFFA000),
    textSuccess = Color(0xFF388E3C),
    iconPrimary = Color(0xFF000000),
    iconSecondary = Color(0xFF757575),
    iconTertiary = Color(0xFFBDBDBD),
    buttonPrimaryBackground = Color(0xFFFFA500),
    buttonPrimaryText = Color(0xFFFFFFFF),
    buttonSecondaryBackground = Color(0xFFFFC107),
    buttonSecondaryText = Color(0xFF000000),
    buttonTertiaryBackground = Color(0xFF6200EE),
    buttonTertiaryText = Color(0xFFFFFFFF),
    divider = Color(0xFFBDBDBD),
)

val DarkColors = AppColors(
    primary = Color(0xFFFFA500),
    secondary = Color(0xFFFFC107),
    tertiary = Color(0xFFBB86FC),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFFFFFFFF),
    onTertiary = Color(0xFF000000),
    textPrimary = Color(0xFFFFFFFF),
    textSecondary = Color(0xFFBDBDBD),
    textTertiary = Color(0xFF757575),
    textError = Color(0xFFEF9A9A),
    textWarning = Color(0xFFFFD54F),
    textSuccess = Color(0xFFA5D6A7),
    iconPrimary = Color(0xFFFFFFFF),
    iconSecondary = Color(0xFFBDBDBD),
    iconTertiary = Color(0xFF757575),
    buttonPrimaryBackground = Color(0xFFFFA500),
    buttonPrimaryText = Color(0xFF000000),
    buttonSecondaryBackground = Color(0xFFFFC107),
    buttonSecondaryText = Color(0xFF000000),
    buttonTertiaryBackground = Color(0xFFBB86FC),
    buttonTertiaryText = Color(0xFF000000),
    divider = Color(0xFF424242),
)
