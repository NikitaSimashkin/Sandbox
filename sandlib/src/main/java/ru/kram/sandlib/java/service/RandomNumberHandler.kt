package ru.kram.sandlib.java.service

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import ru.kram.common.service_contract.RandomMessengerServiceContract
import ru.kram.common.util.process.ProcessNameProvider
import java.lang.ref.WeakReference

class RandomNumberHandler(service: IRandomNumberService): Handler(Looper.getMainLooper()) {

	private val service = WeakReference(service)

	override fun handleMessage(msg: Message) {
		when (msg.what) {
			RandomMessengerServiceContract.GENERATE_NEW_NUMBER -> handleGenerateNewNumber()
			RandomMessengerServiceContract.REQUEST_NUMBER -> handleRequestNumber(msg)
			else -> super.handleMessage(msg)
		}
	}

	private fun handleRequestNumber(msg: Message) {
		Log.d(TAG, "GET_NUMBER, ${ProcessNameProvider.getThreadAndProcessInfo()}")
		val service = service.get()
		if (service != null) {
			if (msg.replyTo != null) {
				msg.replyTo.send(
					Message.obtain(
						null,
						RandomMessengerServiceContract.RECEIVE_NUMBER,
						service.getNumber(),
						-1
					)
				)
			}
		} else {
			Log.d(TAG, "serivce ref is null")
		}
	}

	private fun handleGenerateNewNumber() {
		Log.d(TAG, "GENERATE_NEW_NUMBER, ${ProcessNameProvider.getThreadAndProcessInfo()}")
		if (service.get() != null) {
			service.get()?.updateNumber()
		} else {
			Log.d(TAG, "serivce ref is null")
		}
	}

	interface IRandomNumberService {
		fun updateNumber()
		fun getNumber(): Int
	}

	companion object {
		private const val TAG = "RandomNumberHandler"
	}
}