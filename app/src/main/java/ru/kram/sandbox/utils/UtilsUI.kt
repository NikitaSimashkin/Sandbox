package ru.kram.sandbox.utils

import android.content.Context
import androidx.fragment.app.Fragment

fun Fragment.pxToDp(px: Int): Int {
	return requireContext().pxToDp(px)
}

fun Context.pxToDp(px: Int): Int {
	val density = resources.displayMetrics.density
	return (px / density).toInt()
}