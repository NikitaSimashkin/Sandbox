package ru.kram.sandbox.features.tvcompose.details.presentation.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class AppTypography(
    val headlinePrimary: TextStyle,
    val headlineSecondary: TextStyle,
    val headlineTertiary: TextStyle,
    val bodyPrimary: TextStyle,
    val bodySecondary: TextStyle,
    val bodyTertiary: TextStyle,
    val buttonPrimary: TextStyle,
    val buttonSecondary: TextStyle,
    val buttonTertiary: TextStyle,
    val captionPrimary: TextStyle,
    val captionSecondary: TextStyle,
    val overline: TextStyle
)

val LightTypography = AppTypography(
    headlinePrimary = TextStyle(
        fontSize = 48.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 56.sp
    ),
    headlineSecondary = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 44.sp
    ),
    headlineTertiary = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 32.sp
    ),
    bodyPrimary = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 28.sp
    ),
    bodySecondary = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 24.sp
    ),
    bodyTertiary = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 22.sp
    ),
    buttonPrimary = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 28.sp
    ),
    buttonSecondary = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 26.sp
    ),
    buttonTertiary = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 24.sp
    ),
    captionPrimary = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp
    ),
    captionSecondary = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 18.sp
    ),
    overline = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 14.sp
    )
)

val DarkTypography = LightTypography