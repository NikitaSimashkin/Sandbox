package ru.kram.sandbox.features.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class CountBackgroundService: Service() {

	private val scope = CoroutineScope(Dispatchers.IO)

	override fun onBind(intent: Intent?): IBinder? {
		return null
	}

	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand")
		scope.launch {
			for (i in 0..100) {
                Timber.d("onStartCommand $i")
                delay(2000)
			}
			stopSelf()
		}
		return START_REDELIVER_INTENT
	}

	override fun onCreate() {
        Timber.d("onCreate")
		super.onCreate()
	}

	override fun onDestroy() {
        Timber.d("onDestroy")
		scope.cancel()
		super.onDestroy()
	}

	companion object {
		private val TAG = "CountBackgroundService"
	}
}