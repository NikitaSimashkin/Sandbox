package ru.kram.sandbox.features.player

import android.Manifest
import android.content.Context
import android.content.pm.ActivityInfo
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ConcatenatingMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.sandbox.features.player.databinding.FragmentPlayerBinding

class PlayerFragment: Fragment(R.layout.fragment_player) {

	private val binding: FragmentPlayerBinding by viewBinding(FragmentPlayerBinding::bind)
	private val player by lazy { ExoPlayer.Builder(requireContext()).build() }

	override fun onAttach(context: Context) {
		super.onAttach(context)
		registerForActivityResult(ActivityResultContracts.RequestPermission()) {}.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
	}

	override fun onStart() {
		super.onStart()
		startPlayer()
		requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
	}

	override fun onStop() {
		super.onStop()
		player.release()
		requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
	}

    @OptIn(UnstableApi::class)
    private fun startPlayer() {
		val mediaSources = createMediaSources(listOf("asset:///video1.mp4", "asset:///video2.mp4"))
		val concatenatingMediaSource = ConcatenatingMediaSource(*mediaSources.toTypedArray())
		player.apply {
			setVideoSurfaceView(binding.surfaceView)
			setMediaSource(concatenatingMediaSource)
			playWhenReady = true
			repeatMode = Player.REPEAT_MODE_ALL
			prepare()
		}
	}

    @OptIn(UnstableApi::class)
    private fun createMediaSources(URIs: List<String>): List<MediaSource> {
		val factory = DefaultDataSource.Factory(requireContext())
		return URIs.map { ProgressiveMediaSource.Factory(factory).createMediaSource(MediaItem.fromUri(it)) }
	}
}