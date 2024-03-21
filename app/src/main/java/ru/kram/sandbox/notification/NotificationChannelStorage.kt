package ru.kram.sandbox.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import ru.kram.sandbox.jobscheduler.NotificationJobService
import ru.kram.sandbox.pendingintent.PendingIntentFragment
import ru.kram.sandbox.service.CountForegroundService
import ru.kram.sandbox.wokmanager.NotificationWorker

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
			CountForegroundService.CHANNEL_ID,
			CountForegroundService.CHANNEL_NAME,
			NotificationManager.IMPORTANCE_HIGH
		)
	}

	@RequiresApi(Build.VERSION_CODES.O)
	private fun createPendingIntentNotificationChannel(): NotificationChannel {
		return NotificationChannel(
			PendingIntentFragment.CHANNEL_ID,
			PendingIntentFragment.CHANNEL_NAME,
			NotificationManager.IMPORTANCE_HIGH
		)
	}
}