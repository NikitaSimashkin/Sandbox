package ru.kram.ipctestapp2.randomservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Messenger
import android.util.Log
import ru.kram.sandbox.common.utils.ProcessNameProvider
import timber.log.Timber
import java.lang.RuntimeException

class RandomMessengerService: Service() {

	private val TAG = "RandomMessengerService${hashCode()}"
	private var number: Int? = null

	init {
		Timber.tag(TAG).d("init, ${ProcessNameProvider.getThreadAndProcessInfo()}")
	}

	override fun onBind(intent: Intent?): IBinder {
		Timber.tag(TAG).d("onBind, ${ProcessNameProvider.getThreadAndProcessInfo()}")
		return Messenger(RandomNumberHandler(RandomNumberServiceImpl())).binder
	}

	override fun onCreate() {
		Timber.tag(TAG).d("onCreate")
		super.onCreate()
	}

	override fun onStart(intent: Intent?, startId: Int) {
		Timber.tag(TAG).d("onStart")
		super.onStart(intent, startId)
	}

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		Timber.tag(TAG).d("onStartCommand")
		return super.onStartCommand(intent, flags, startId)
	}

	override fun onDestroy() {
		Timber.tag(TAG).d("onDestroy")
		super.onDestroy()
	}

	override fun onUnbind(intent: Intent?): Boolean {
		Timber.tag(TAG).d("onUnbind")
		return false
	}

	override fun onRebind(intent: Intent?) {
		Timber.tag(TAG).d("onRebind")
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