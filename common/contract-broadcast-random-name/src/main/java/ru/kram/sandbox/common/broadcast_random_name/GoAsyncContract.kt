package ru.kram.sandbox.common.broadcast_random_name

import android.content.Intent
import android.content.IntentFilter

object GoAsyncContract {
	private const val ACTION = "ru.kram.sandbox.action.go_async"

	fun getReceiverIntent(): Intent {
		return Intent(ACTION)
	}

	fun getReceiveIntentFilter(): IntentFilter {
		return IntentFilter(ACTION)
	}

}