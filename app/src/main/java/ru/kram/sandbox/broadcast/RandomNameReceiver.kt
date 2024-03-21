package ru.kram.sandbox.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import ru.kram.broadcast.random_name.RandomNameContract

class RandomNameReceiver: BroadcastReceiver() {

	override fun onReceive(context: Context, intent: Intent) {
		Log.d(TAG, "onReceive")
		if (intent.action != RandomNameContract.ACTION) return

		val name = intent.getStringExtra(RandomNameContract.NAME_KEY) ?: RandomNameContract.NAME_DEFAULT
		Toast.makeText(context, "RandomNameReceiver $name", Toast.LENGTH_SHORT).show()
	}

	companion object {
		private const val TAG = "RandomNameReceiver"
	}
}