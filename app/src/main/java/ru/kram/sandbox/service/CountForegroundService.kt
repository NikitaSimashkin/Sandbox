package ru.kram.sandbox.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.kram.sandbox.MainActivity
import ru.kram.sandbox.R
import ru.kram.sandbox.compose.edgetoedge.EdgeToEdgeActivity
import timber.log.Timber

private var counter = 0

class CountForegroundService: Service() {

	private val scope = CoroutineScope(Dispatchers.IO)

	override fun onBind(intent: Intent?): IBinder? {
		return null
	}

	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		Log.d(TAG, "onStartCommand")
		when(intent.action) {
			ACTION_START -> {
				startForeground(NOTIFICATION_ID, createNotification())
				scope.launch {
					for (i in 0..100) {
						Log.d(TAG, "onStartCommand $i")
						delay(2000)
					}
					stopSelf()
				}
			}
			ACTION_STOP -> stopSelf()
			ACTION_STOP_FOREGROUND_DETACH -> stopForeground(STOP_FOREGROUND_DETACH)
			ACTION_STOP_FOREGROUND_REMOVE -> stopForeground(STOP_FOREGROUND_REMOVE)
			ACTION_START_ACTIVITY -> startActivity()
		}

		return START_REDELIVER_INTENT
	}

	override fun onCreate() {
		Log.d(TAG, "onCreate")
		super.onCreate()
	}

	override fun onDestroy() {
		Log.d(TAG, "onDestroy")
		scope.cancel()
		super.onDestroy()
	}

	private fun createNotification(): Notification {
		val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
			.setSmallIcon(R.drawable.nikita)
			.setContentTitle("Content title")
			.setContentText("Content text")
			.setPriority(NotificationCompat.PRIORITY_DEFAULT)
		return builder.build()
	}

	private fun startActivity() {
		Timber.tag(TAG).d("startActivity")
		Handler(Looper.getMainLooper()).postDelayed( {
			Timber.tag(TAG).d("startActivity postDelayed")
			val intent = Intent(this, EdgeToEdgeActivity::class.java)
			intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
			startActivity(intent)
		}, 3000)
	}

	companion object {
		private val TAG = "CountForegroundService ${counter++}"

		private const val NOTIFICATION_ID = 1
		const val CHANNEL_ID = "count_service_channel_id"
		const val CHANNEL_NAME = "count_service_channel_name"

		const val ACTION_START = "ru.kram.sandbox.service.CountForegroundService.ACTION_START"
		const val ACTION_STOP = "ru.kram.sandbox.service.CountForegroundService.ACTION_STOP"
		const val ACTION_STOP_FOREGROUND_DETACH = "ru.kram.sandbox.service.CountForegroundService.ACTION_STOP_FOREGROUND_DETACH"
		const val ACTION_STOP_FOREGROUND_REMOVE = "ru.kram.sandbox.service.CountForegroundService.ACTION_STOP_FOREGROUND_REMOVE"
		const val ACTION_START_ACTIVITY = "ru.kram.sandbox.service.CountForegroundService.START_ACTIVITY"
	}
}