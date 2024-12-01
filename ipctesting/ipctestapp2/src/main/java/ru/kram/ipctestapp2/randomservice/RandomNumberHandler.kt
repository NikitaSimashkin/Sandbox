package ru.kram.ipctestapp2.randomservice

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import ru.kram.sandbox.service_contract.RandomMessengerServiceContract
import ru.kram.sandbox.common.utils.ProcessNameProvider
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
		Timber.d("GET_NUMBER, ${ProcessNameProvider.getThreadAndProcessInfo()}")
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
			Timber.d("serivce ref is null")
		}
	}

	private fun handleGenerateNewNumber() {
		Timber.d("GENERATE_NEW_NUMBER, ${ProcessNameProvider.getThreadAndProcessInfo()}")
		if (service.get() != null) {
			service.get()?.updateNumber()
		} else {
			Timber.d("serivce ref is null")
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