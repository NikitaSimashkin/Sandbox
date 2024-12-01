package ru.kram.sandbox.common.broadcast_random_name

import android.content.Intent
import android.content.IntentFilter

object PeekServiceContract {
	private const val ACTION = "ru.kram.sandbox.action.peek_service"

	fun getReceiverIntent(): Intent {
		return Intent(ACTION)
	}

	fun getReceiverIntentFilter(): IntentFilter {
		return IntentFilter(ACTION)
	}

}