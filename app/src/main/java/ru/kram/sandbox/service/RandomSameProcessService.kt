package ru.kram.sandbox.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.kram.common.util.process.ProcessNameProvider

class RandomSameProcessService: Service() {

	private val numberFlow = MutableStateFlow(0)
	private val scope = CoroutineScope(Dispatchers.IO)
	private var isRunning = false

	override fun onBind(intent: Intent?): IBinder {
		Log.d(TAG, "onBind, ${ProcessNameProvider.getThreadAndProcessInfo()}")
		if (!isRunning) {
			isRunning = true
			startRandomNumberChange()
		}
		return Binder(numberFlow)
	}

	override fun onCreate() {
		Log.d(TAG, "onCreate")
		super.onCreate()
	}

	override fun onStart(intent: Intent?, startId: Int) {
		Log.d(TAG, "onStart")
		super.onStart(intent, startId)
	}

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		Log.d(TAG, "onStartCommand")
		return super.onStartCommand(intent, flags, startId)
	}

	override fun onDestroy() {
		Log.d(TAG, "onDestroy")
		super.onDestroy()
	}

	override fun onUnbind(intent: Intent?): Boolean {
		Log.d(TAG, "onUnbind")
		scope.cancel()
		isRunning = false
		return false // it true - in next time will be called on Rebind
	}

	override fun onRebind(intent: Intent?) {
		Log.d(TAG, "onRebind")
		super.onRebind(intent)
	}

	private fun startRandomNumberChange() {
		scope.launch {
			while (isRunning) {
				val value = (0..100).random()
				numberFlow.emit(value)
				Log.d(TAG, "emit $value")
				delay(1000)
			}
		}
	}

	class Binder(
		private val numberFlow: StateFlow<Int>
	): android.os.Binder() {
		fun getNumberFlow(): StateFlow<Int> {
			Log.d(TAG, "getNumberFlow ${ProcessNameProvider.getThreadAndProcessInfo()}")
			return numberFlow
		}

		override fun linkToDeath(recipient: IBinder.DeathRecipient, flags: Int) {
			Log.d(TAG, "linkToDeath ${ProcessNameProvider.getThreadAndProcessInfo()}")
			super.linkToDeath(recipient, flags)
		}

		override fun unlinkToDeath(recipient: IBinder.DeathRecipient, flags: Int): Boolean {
			Log.d(TAG, "unlinkToDeath 	${ProcessNameProvider.getThreadAndProcessInfo()}")
			return super.unlinkToDeath(recipient, flags)
		}
	}

	companion object {
		private val TAG = "RandomSameProcessService${hashCode()}"
	}
}