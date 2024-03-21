package ru.kram.broadcast.random_name

import android.content.Intent
import android.content.IntentFilter

object GoAsyncContract {
	private const val ACTION = "ru.kram.broadcast.action.go_async"

	fun getReceiverIntent(): Intent {
		return Intent(ACTION)
	}

	fun getReceiveIntentFilter(): IntentFilter {
		return IntentFilter(ACTION)
	}

}