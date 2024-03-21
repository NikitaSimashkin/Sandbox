package ru.kram.broadcast.random_name

import android.content.Intent
import android.content.IntentFilter

object PeekServiceContract {
	private const val ACTION = "ru.kram.broadcast.action.peek_service"

	fun getReceiverIntent(): Intent {
		return Intent(ACTION)
	}

	fun getReceiverIntentFilter(): IntentFilter {
		return IntentFilter(ACTION)
	}

}