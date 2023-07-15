package ru.kram.sandbox

import android.Manifest
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.doOnPreDraw
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ConcatenatingMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import ru.kram.sandbox.carousel.CarouselFragment
import ru.kram.sandbox.databinding.ActivityAddBinding
import ru.kram.sandbox.recyclerfocus.RecyclerFragment

class MainActivity : AppCompatActivity() {

	private val player by lazy { ExoPlayer.Builder(this).build() }

	private val playerFlag = false
	private val addFlag = false
	private val appCompatTextViewFlag = false
	private val recyclerfocusFlag = false
	private val carousel = true

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		if (savedInstanceState == null) {
			if (playerFlag) {
				setContentView(R.layout.activity_main)
				testExo()
			} else if (addFlag) {
				setContentView(R.layout.activity_add)
				showAdd()
			} else if (appCompatTextViewFlag) {
				setContentView(R.layout.activity_appcompat_textview)
				testAppCompat()
			} else if (recyclerfocusFlag) {
				requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
				supportFragmentManager.beginTransaction().apply {
					add(android.R.id.content, RecyclerFragment())
					commit()
				}
			} else if (carousel) {
				supportFragmentManager.beginTransaction().apply {
					add(android.R.id.content, CarouselFragment())
					commit()
				}
			}
		}
	}

	override fun onStart() {
		super.onStart()
		supportActionBar?.hide()
	}

	private fun showAdd() {
		val binding = ActivityAddBinding.bind(findViewById(R.id.add_root))
		binding.bannerLayout.doOnPreDraw {
			binding.banner.setAdSize(AdSize.inlineSize(pxToDp(it.width), pxToDp(it.height)))
			binding.banner.setAdUnitId("demo-banner-yandex")
			binding.banner.setBannerAdEventListener(object : BannerAdEventListener{
				override fun onAdLoaded() {
					Log.d("Nikita", "onAdLoaded")
				}

				override fun onAdFailedToLoad(p0: AdRequestError) {
					Log.d("Nikita", p0.toString())
				}

				override fun onAdClicked() {
					Log.d("Nikita", "onAdClicked")
				}

				override fun onLeftApplication() {
					Log.d("Nikita", "onLeftApplication")
				}

				override fun onReturnedToApplication() {
					Log.d("Nikita", "onReturnedToApplication")
				}

				override fun onImpression(p0: ImpressionData?) {
					Log.d("Nikita", p0.toString())
				}

			})

			binding.banner.loadAd(AdRequest.Builder().build())
		}
	}

	@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
	private fun testExo() {
		registerForActivityResult(ActivityResultContracts.RequestPermission()) {}.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
		val surfaceView = findViewById<SurfaceView>(R.id.surface_view)
		val factory = DefaultDataSource.Factory(this)
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

	private fun testAppCompat() {
		var str = "123"
		val text = findViewById<TextView>(R.id.appcompat_textview)
		text.postDelayed({
			text.text = str
			str += str
		}, 3000)
		text.postDelayed({
			text.text = str
			str += str
		}, 6000)
		text.postDelayed({
			text.text = str
			str += str
		}, 9000)
	}

	private fun testCarousel() {

	}

	override fun onStop() {
		super.onStop()
		player.release()
	}

	private fun pxToDp(px: Int): Int {
		val density = resources.displayMetrics.density
		return (px / density).toInt()
	}
}
