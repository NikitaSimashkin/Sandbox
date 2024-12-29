package ru.kram.sandbox.features.tvcompose.utils

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter

sealed class ImageWrapper {
    data class Resource(@DrawableRes val resId: Int) : ImageWrapper()
    data class Url(val url: String) : ImageWrapper()
}

@Composable
fun ImageWrapper.getPainter(): Painter {
    return when (this) {
        is ImageWrapper.Resource -> painterResource(this.resId)
        is ImageWrapper.Url -> rememberAsyncImagePainter(this.url)
    }
}