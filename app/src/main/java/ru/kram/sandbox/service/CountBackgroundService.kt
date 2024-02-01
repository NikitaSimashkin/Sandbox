package ru.kram.sandbox.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountBackgroundService: Service() {

	private val scope = CoroutineScope(Dispatchers.IO)

	override fun onBind(intent: Intent?): IBinder? {
		return null
	}

	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		Log.d(TAG, "onStartCommand")
		scope.launch {
			for (i in 0..100) {
				Log.d(TAG, "onStartCommand $i")
				delay(2000)
			}
			stopSelf()
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

	companion object {
		private val TAG = "CountBackgroundService"
	}
}