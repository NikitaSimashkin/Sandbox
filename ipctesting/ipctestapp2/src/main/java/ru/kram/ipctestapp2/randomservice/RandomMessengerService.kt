package ru.kram.ipctestapp2.randomservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Messenger
import android.util.Log
import ru.kram.sandbox.common.utils.ProcessNameProvider
import java.lang.RuntimeException

class RandomMessengerService: Service() {

	private val TAG = "RandomMessengerService${hashCode()}"
	private var number: Int? = null

	init {
		Timber.d("init, ${ProcessNameProvider.getThreadAndProcessInfo()}")
	}

	override fun onBind(intent: Intent?): IBinder {
		Timber.d("onBind, ${ProcessNameProvider.getThreadAndProcessInfo()}")
		return Messenger(RandomNumberHandler(RandomNumberServiceImpl())).binder
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
		return false
	}

	override fun onRebind(intent: Intent?) {
		Timber.d("onRebind")
		super.onRebind(intent)
	}

	private fun generateRandomNumber(): Int {
		return (0..100).random()
	}

	private inner class RandomNumberServiceImpl: RandomNumberHandler.IRandomNumberService {
		override fun getNumber(): Int {
			return number ?: throw RuntimeException("No number!")
		}

		override fun updateNumber() {
			number = generateRandomNumber()
		}
	}
}