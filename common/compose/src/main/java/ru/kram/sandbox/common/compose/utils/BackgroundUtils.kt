package ru.kram.sandbox.common.compose.utils

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val noButtonColor @Composable get() =
    ButtonDefaults.buttonColors(containerColor = Color.Transparent)

@Composable
fun backgroundButtonColor(color: Color) = ButtonDefaults.buttonColors(containerColor = color)