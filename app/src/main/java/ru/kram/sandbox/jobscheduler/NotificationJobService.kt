package ru.kram.sandbox.jobscheduler

import android.annotation.SuppressLint
import android.app.Notification
import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("SpecifyJobSchedulerIdRange")
class NotificationJobService: JobService() {

	private val scope = CoroutineScope(Dispatchers.IO)

	@SuppressLint("MissingPermission")
	override fun onStartJob(params: JobParameters): Boolean {
		Log.d(TAG, "onStartJob")
		scope.launch {
			delay(5000)
			Log.d(TAG, "onStartJob: show notification")
			val notification = createNotification()
			with(NotificationManagerCompat.from(this@NotificationJobService)) {
				notify(NOTIFICATION_ID, notification)
			}
			jobFinished(params, false)
		}
		return true
	}

	@SuppressLint("MissingPermission")
	override fun onStopJob(params: JobParameters): Boolean {
		scope.cancel()
		with(NotificationManagerCompat.from(this@NotificationJobService)) {
			cancel(NOTIFICATION_ID)
		}
		return false
	}

	private fun createNotification(): Notification {
		Log.d(TAG, "createNotification")
		return NotificationCompat.Builder(this, CHANNEL_ID)
			.setContentTitle("NotificationJobService")
			.setContentText("NotificationJobService")
			.setSmallIcon(android.R.drawable.ic_dialog_info)
			.build()
	}

	companion object {
		const val TAG = "NotificationJobService"

		const val JOB_ID = 1

		const val CHANNEL_ID = "NotificationJobService"
		const val CHANNEL_NAME = "NotificationJobService"
		const val NOTIFICATION_ID = 1
	}
}