package ru.kram.ipctestapp2.randomservice

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
import ru.kram.sandbox.common.utils.ProcessNameProvider

class RandomService: Service() {

	private val numberFlow = MutableStateFlow(0)
	private val scope = CoroutineScope(Dispatchers.IO)
	private var isRunning = false

	override fun onBind(intent: Intent?): IBinder {
		Timber.d("onBind, ${ProcessNameProvider.getThreadAndProcessInfo()}")
		if (!isRunning) {
			isRunning = true
			startRandomNumberChange()
		}
		return Binder(numberFlow)
	}

	override fun onCreate() {
		Timber.d("onCreate")
		super.onCreate()
	}

	override fun onStart(intent: Intent?, startId: Int) {
		Timber.d("onStart")
		super.onStart(intent, startId)
	}

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		Timber.d("onStartCommand")
		return super.onStartCommand(intent, flags, startId)
	}

	override fun onDestroy() {
		Timber.d("onDestroy")
		super.onDestroy()
	}

	override fun onUnbind(intent: Intent?): Boolean {
		Timber.d("onUnbind")
		scope.cancel()
		isRunning = false
		return false // it true - in next time will be called on Rebind
	}

	override fun onRebind(intent: Intent?) {
		Timber.d("onRebind")
		super.onRebind(intent)
	}

	private fun startRandomNumberChange() {
		scope.launch {
			while (isRunning) {
				val value = (0..100).random()
				numberFlow.emit(value)
				Timber.d("emit $value")
				delay(10000)
			}
		}
	}

	class Binder(
		private val numberFlow: StateFlow<Int>
	): android.os.Binder() {
		fun getNumberFlow(): StateFlow<Int> {
			Timber.d("getNumberFlow ${ProcessNameProvider.getThreadAndProcessInfo()}")
			return numberFlow
		}

		override fun linkToDeath(recipient: IBinder.DeathRecipient, flags: Int) {
			Timber.d("linkToDeath ${ProcessNameProvider.getThreadAndProcessInfo()}")
			super.linkToDeath(recipient, flags)
		}

		override fun unlinkToDeath(recipient: IBinder.DeathRecipient, flags: Int): Boolean {
			Timber.d("unlinkToDeath 	${ProcessNameProvider.getThreadAndProcessInfo()}")
			return super.unlinkToDeath(recipient, flags)
		}
	}

	companion object {
		private val TAG = "RandomNumberService${hashCode()}"
	}
}