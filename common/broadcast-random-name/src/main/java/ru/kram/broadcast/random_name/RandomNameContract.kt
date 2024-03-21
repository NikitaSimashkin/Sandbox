package ru.kram.broadcast.random_name

import android.content.ComponentName
import android.content.Intent

object RandomNameContract {
	const val ACTION = "ru.kram.broadcast.action.random_name"

	const val NAME_KEY = "ru.kram.broadcast.random_name.name"
	const val NAME_DEFAULT = "NoName"

	const val PACKAGE_RECEIVER = "ru.kram.sandbox"
	const val CLASS_RECEIVER = "ru.kram.sandbox.broadcast.RandomNameReceiver"

	const val SEND_PERMISSION = "ru.kram.broadcast.random_name.permission.send"
	const val RECEIVE_PERMISSION = "ru.kram.broadcast.random_name.permission.receive"

	fun getReceiveIntent(name: RandomName): Intent {
		return Intent(ACTION).apply {
			component = ComponentName(PACKAGE_RECEIVER, CLASS_RECEIVER)
			putExtra(NAME_KEY, name.name)
		}
	}

	data class RandomName(val name: String)
}