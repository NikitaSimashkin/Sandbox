package ru.kram.sandbox.features.tvcompose.details.presentation.model

import android.net.Uri

data class VideoItem(
    val uri: Uri,
    val isSoundEnabled: Boolean,
    val isLooping: Boolean,
    val format: Format,
) {
    enum class Format {
        MP4,
        HLS
    }
}