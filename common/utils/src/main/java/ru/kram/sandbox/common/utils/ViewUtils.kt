package ru.kram.sandbox.common.utils

import android.content.Context
import androidx.fragment.app.Fragment

fun Context.pxToDp(px: Int): Int {
    return (px / resources.displayMetrics.density).toInt()
}

fun Context.dpToPx(dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
}

fun Fragment.pxToDp(px: Int): Int {
    return requireContext().pxToDp(px)
}

fun Fragment.dpToPx(dp: Int): Int {
    return requireContext().dpToPx(dp)
}