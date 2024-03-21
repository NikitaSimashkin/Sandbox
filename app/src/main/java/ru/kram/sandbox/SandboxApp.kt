package ru.kram.sandbox

import android.app.Application
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.work.Configuration
import ru.kram.sandbox.notification.NotificationChannelStorage
import java.util.concurrent.Executors

class SandboxApp: Application(), Configuration.Provider {

	private val notificationChannelStorage = NotificationChannelStorage()

	override val workManagerConfiguration: Configuration
		get() = Configuration.Builder()
			.setExecutor(Executors.newFixedThreadPool(5))
			.setMinimumLoggingLevel(Log.DEBUG)
			.build()

	override fun onCreate() {
		Log.d(TAG, "onCreate")
		super.onCreate()
		createNotificationChannels()
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