package ru.kram.sandbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ConcatenatingMediaSource2
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class MainActivity : AppCompatActivity() {

	val player by lazy { ExoPlayer.Builder(this).build() }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
	}

	override fun onStart() {
		super.onStart()
		supportActionBar?.hide()
		registerForActivityResult(ActivityResultContracts.RequestPermission()) {}.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
		val surfaceView = findViewById<SurfaceView>(R.id.surface_view)
		val factory = DefaultDataSourceFactory(this)
		val video1 = ProgressiveMediaSource.Factory(factory).createMediaSource(
			MediaItem.fromUri("asset:///video1.mp4")
		)
		val video2 = ProgressiveMediaSource.Factory(factory).createMediaSource(
			MediaItem.fromUri("asset:///video2.mp4")
		)
		val mediaStream = ConcatenatingMediaSource(video1, video2)
		player.setVideoSurfaceView(surfaceView)
		player.setMediaSource(mediaStream)
		player.playWhenReady = true
		player.repeatMode = Player.REPEAT_MODE_ALL
		player.prepare()
	}

	override fun onStop() {
		super.onStop()
		player.release()
	}
}