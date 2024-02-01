package ru.kram.sandbox

import android.app.Application
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import ru.kram.sandbox.notification.NotificationChannelStorage

class SandboxApp: Application() {

	private val notificationChannelStorage = NotificationChannelStorage()

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