package ru.kram.sandbox.utils

import android.content.Context
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment

fun Fragment.pxToDp(px: Int): Int {
    return requireContext().pxToDp(px)
}

fun Context.pxToDp(px: Int): Int {
    val density = resources.displayMetrics.density
    return (px / density).toInt()
}

val noButtonColor @Composable get() =
    ButtonDefaults.buttonColors(containerColor = Color.Transparent)

@Composable
fun backgroundButtonColor(color: Color) = ButtonDefaults.buttonColors(containerColor = color)