package ru.kram.sandlib.java.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Messenger
import android.util.Log
import ru.kram.common.util.process.ProcessNameProvider
import java.lang.RuntimeException

class RandomMessengerService: Service() {

	private val TAG = "RandomMessengerService${hashCode()}"
	private var number: Int? = null

	init {
		Log.d(TAG, "init, ${ProcessNameProvider.getThreadAndProcessInfo()}")
	}

	override fun onBind(intent: Intent?): IBinder {
		Log.d(TAG, "onBind, ${ProcessNameProvider.getThreadAndProcessInfo()}")
		return Messenger(RandomNumberHandler(RandomNumberServiceImpl())).binder
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
		return false
	}

	override fun onRebind(intent: Intent?) {
		Log.d(TAG, "onRebind")
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