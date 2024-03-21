package ru.kram.sandbox.wokmanager

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.impl.utils.taskexecutor.TaskExecutor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

	private val scope = CoroutineScope(Dispatchers.IO)

	@SuppressLint("MissingPermission")
	override fun doWork(): Result {
		Log.d(TAG, "doWork")
		try {
			NotificationManagerCompat.from(applicationContext).notify(NOTIFICATION_ID, createNotification())
		} catch (ex: Exception) {
			return Result.failure(); //или Result.retry()
		}
		return Result.success()
	}

	override fun onStopped() {
		scope.cancel()
		NotificationManagerCompat.from(applicationContext).cancel(NOTIFICATION_ID)
		super.onStopped()
	}

	private fun createNotification(): Notification {
		Log.d(TAG, "createNotification")
		return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
			.setContentTitle("NotificationWorker")
			.setContentText("NotificationWorker")
			.setSmallIcon(android.R.drawable.ic_delete)
			.build()
	}

	companion object {
		const val TAG = "NotificationWorker"

		const val CHANNEL_ID = "NotificationWorker"
		const val CHANNEL_NAME = "NotificationWorker"

		const val NOTIFICATION_ID = 1
	}
}