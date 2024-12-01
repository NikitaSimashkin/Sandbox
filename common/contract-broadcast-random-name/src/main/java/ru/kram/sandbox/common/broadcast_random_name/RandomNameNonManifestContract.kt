package ru.kram.sandbox.common.broadcast_random_name

import android.content.Intent
import android.content.IntentFilter

object RandomNameNonManifestContract {

	const val ACTION = "ru.kram.sandbox.action.random_name_non_manifest"

	const val NAME_KEY = "ru.kram.sandbox.random_name.name"
	const val NAME_DEFAULT = "NoName"

	const val SEND_PERMISSION = "ru.kram.sandbox.permission.random_name_send"
	const val RECEIVE_PERMISSION = "ru.kram.sandbox.permission.random_name_receive"

	fun getReceiveIntent(name: RandomName): Intent {
		return Intent(ACTION).putExtra(NAME_KEY, name.value)
	}

	fun getReceiveIntentFilter(): IntentFilter {
		return IntentFilter(ACTION)
	}

	data class RandomName(val value: String)
}