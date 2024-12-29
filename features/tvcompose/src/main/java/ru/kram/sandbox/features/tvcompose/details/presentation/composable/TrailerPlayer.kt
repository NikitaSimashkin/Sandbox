@file:SuppressWarnings("UnstableApi")

package ru.kram.sandbox.features.tvcompose.details.presentation.composable

import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import ru.kram.sandbox.features.tvcompose.details.presentation.model.VideoItem
import ru.kram.sandbox.features.tvcompose.utils.rememberExoPlayer
import timber.log.Timber

@OptIn(UnstableApi::class)
@Composable
fun TrailerPlayer(
    videoItem: VideoItem,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val animationDuration = 500

    var isVideoReady by remember { mutableStateOf(false) }

    val mediaSource by remember(videoItem.uri) {
        val factory = DefaultDataSource.Factory(context)
        val mediaItemBuilder = MediaItem.Builder()
            .setUri(videoItem.uri)

        val mediaSource = when(videoItem.format) {
            VideoItem.Format.MP4 -> {
                ProgressiveMediaSource.Factory(factory).createMediaSource(
                    mediaItemBuilder
                        .setMimeType(MimeTypes.VIDEO_MP4)
                        .build()
                )
            }
            VideoItem.Format.HLS -> {
                HlsMediaSource.Factory(factory).createMediaSource(
                    mediaItemBuilder
                        .setMimeType(MimeTypes.APPLICATION_M3U8)
                        .build()
                )
            }
        }
        mutableStateOf(mediaSource)
    }

    val exoPlayer = rememberExoPlayer()

    LaunchedEffect(mediaSource) {
        Timber.d("loadVideo: id=${mediaSource.mediaItem}")
        val mediaSourceRef = mediaSource
        exoPlayer.prepare()
        exoPlayer.clearMediaItems()
        exoPlayer.setMediaSource(mediaSourceRef)
    }

    LaunchedEffect(videoItem.isSoundEnabled) {
        exoPlayer.volume = if (videoItem.isSoundEnabled) 1f else 0f
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            Timber.d("onDispose")
            exoPlayer.release()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = !isVideoReady,
            enter = fadeIn(animationSpec = tween(animationDuration)),
            exit = fadeOut(animationSpec = tween(animationDuration))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            )
        }

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    exoPlayer.playWhenReady = true
                    exoPlayer.repeatMode = if (videoItem.isLooping) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF

                    exoPlayer.addListener(
                        object : Player.Listener {
                            override fun onPlaybackStateChanged(playbackState: Int) {
                                Timber.d("onPlaybackStateChanged: $playbackState")
                                isVideoReady = playbackState == Player.STATE_READY
                            }
                        }
                    )
                }
            }
        )
    }
}