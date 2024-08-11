package ru.kram.sandbox

import android.app.Application
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.work.Configuration
import com.vk.recompose.highlighter.RecomposeHighlighterConfig
import com.vk.recompose.logger.RecomposeLoggerConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.kram.sandbox.notification.NotificationChannelStorage
import ru.kram.sandbox.paging3.di.pokemonModule
import timber.log.Timber

class SandboxApp: Application(), Configuration.Provider {

	private val notificationChannelStorage = NotificationChannelStorage()

	override val workManagerConfiguration: Configuration
		get() = Configuration.Builder()
			.setExecutor(Dispatchers.IO.asExecutor())
			.setMinimumLoggingLevel(Log.DEBUG)
			.build()

	override fun onCreate() {
		Log.d(TAG, "onCreate")
		super.onCreate()

		RecomposeLoggerConfig.isEnabled = false
		RecomposeHighlighterConfig.isEnabled = false

		createNotificationChannels()

		startKoin {
			androidContext(this@SandboxApp)
			modules(pokemonModule)
		}

		Timber.plant(Timber.DebugTree())
	}

	private fun createNotificationChannels() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			notificationChannelStorage.getNotificationChannels().forEach {
				getSystemService(NotificationManager::class.java).createNotificationChannel(it)
			}
		}
	}

	companion object {
		private const val TAG = "SandboxApp"
	}
}