package ru.kram.sandbox.head.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import ru.kram.sandbox.features.jobscheduler.NotificationJobService
import ru.kram.sandbox.features.workmanager.NotificationWorker

class NotificationChannelStorage {

	fun getNotificationChannels(): List<NotificationChannel> {
		return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			listOf(
				createCountServiceNotificationChannel(),
				createPendingIntentNotificationChannel(),
				createNotificationJobServiceChannel(),
				createNotificationWorkerChannel()
			)
		} else {
			emptyList()
		}
	}

	@RequiresApi(Build.VERSION_CODES.O)
	fun createNotificationWorkerChannel(): NotificationChannel {
		return NotificationChannel(
			NotificationWorker.CHANNEL_ID,
			NotificationWorker.CHANNEL_NAME,
			NotificationManager.IMPORTANCE_HIGH
		)
	}

	@RequiresApi(Build.VERSION_CODES.O)
	private fun createNotificationJobServiceChannel(): NotificationChannel {
		return NotificationChannel(
			NotificationJobService.CHANNEL_ID,
			NotificationJobService.CHANNEL_NAME,
			NotificationManager.IMPORTANCE_HIGH
		)
	}

	@RequiresApi(Build.VERSION_CODES.O)
	private fun createCountServiceNotificationChannel(): NotificationChannel {
		return NotificationChannel(
			ru.kram.sandbox.features.service.CountForegroundService.CHANNEL_ID,
			ru.kram.sandbox.features.service.CountForegroundService.CHANNEL_NAME,
			NotificationManager.IMPORTANCE_HIGH
		)
	}

	@RequiresApi(Build.VERSION_CODES.O)
	private fun createPendingIntentNotificationChannel(): NotificationChannel {
		return NotificationChannel(
			ru.kram.sandbox.features.pendingintent.PendingIntentFragment.CHANNEL_ID,
			ru.kram.sandbox.features.pendingintent.PendingIntentFragment.CHANNEL_NAME,
			NotificationManager.IMPORTANCE_HIGH
		)
	}
}