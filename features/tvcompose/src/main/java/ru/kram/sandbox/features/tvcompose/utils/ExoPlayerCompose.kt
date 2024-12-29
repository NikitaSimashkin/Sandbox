package ru.kram.sandbox.features.tvcompose.utils

import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer

@OptIn(UnstableApi::class)
@Composable
fun rememberExoPlayer(): ExoPlayer {
    val context = LocalContext.current
    return remember {
        ExoPlayer
            .Builder(context)
            .build()
    }
}