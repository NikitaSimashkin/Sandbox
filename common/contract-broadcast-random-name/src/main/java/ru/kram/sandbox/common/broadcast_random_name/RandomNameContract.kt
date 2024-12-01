package ru.kram.sandbox.common.broadcast_random_name

import android.content.ComponentName
import android.content.Intent

object RandomNameContract {
	const val ACTION = "ru.kram.sandbox.action.random_name"

	const val NAME_KEY = "ru.kram.sandbox.random_name.name"
	const val NAME_DEFAULT = "NoName"

	const val PACKAGE_RECEIVER = "ru.kram.sandbox"
	const val CLASS_RECEIVER = "ru.kram.sandbox.head.broadcast.RandomNameReceiver"

	const val SEND_PERMISSION = "ru.kram.sandbox.permission.random_name_send"
	const val RECEIVE_PERMISSION = "ru.kram.sandbox.permission.random_name_receive"

	fun getReceiveIntent(name: RandomName): Intent {
		return Intent(ACTION).apply {
			component = ComponentName(PACKAGE_RECEIVER, CLASS_RECEIVER)
			putExtra(NAME_KEY, name.name)
		}
	}

	data class RandomName(val name: String)
}