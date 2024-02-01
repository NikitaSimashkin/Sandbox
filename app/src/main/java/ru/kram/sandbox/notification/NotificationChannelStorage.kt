package ru.kram.sandbox.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import ru.kram.sandbox.service.CountForegroundService

class NotificationChannelStorage {

	fun getNotificationChannels(): List<NotificationChannel> {
		return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			listOf(createCountServiceNotificationChannel())
		} else {
			emptyList()
		}
	}

	@RequiresApi(Build.VERSION_CODES.O)
	private fun createCountServiceNotificationChannel(): NotificationChannel {
		return NotificationChannel(
			CountForegroundService.CHANNEL_ID,
			CountForegroundService.CHANNEL_NAME,
			NotificationManager.IMPORTANCE_HIGH
		)
	}
}