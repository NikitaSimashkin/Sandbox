package ru.kram.broadcast.random_name

import android.content.Intent
import android.content.IntentFilter

object RandomNameNonManifestContract {

	const val ACTION = "ru.kram.sandbox.broadcast.action.random_name_non_manifest"

	const val NAME_KEY = "ru.kram.broadcast.random_name.name"
	const val NAME_DEFAULT = "NoName"

	const val SEND_PERMISSION = "ru.kram.broadcast.random_name.permission.send"
	const val RECEIVE_PERMISSION = "ru.kram.broadcast.random_name.permission.receive"

	fun getReceiveIntent(name: RandomName): Intent {
		return Intent(ACTION).apply {
			putExtra(NAME_KEY, name.name)
		}
	}

	fun getReceiveIntentFilter(): IntentFilter {
		return IntentFilter(ACTION)
	}

	data class RandomName(val name: String)
}