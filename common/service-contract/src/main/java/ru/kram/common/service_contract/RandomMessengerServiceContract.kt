package ru.kram.common.service_contract

import android.content.ComponentName
import android.content.Intent

object RandomMessengerServiceContract {

	private const val RANDOM_MESSENGER_SERVICE_PACKAGE = "ru.kram.sandlib"
	private const val RANDOM_MESSENGER_SERVICE_CLASS = "ru.kram.sandlib.java.service.RandomMessengerService"
	private const val RANDOM_MESSENGER_SERVICE_PERMISSION = "ru.kram.sandlib.permission.RANDOM_MESSENGER_SERVICE_START"

	const val GENERATE_NEW_NUMBER = 1
	const val REQUEST_NUMBER = 2
	const val RECEIVE_NUMBER = 3

	fun getRandomMessengerServiceIntent(): Intent {
		return Intent().apply {
			`package` = RANDOM_MESSENGER_SERVICE_PACKAGE
			component = ComponentName(RANDOM_MESSENGER_SERVICE_PACKAGE, RANDOM_MESSENGER_SERVICE_CLASS)
		}
	}
}