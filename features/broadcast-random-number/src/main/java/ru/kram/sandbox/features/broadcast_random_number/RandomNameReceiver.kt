package ru.kram.sandbox.features.broadcast_random_number

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import ru.kram.sandbox.common.broadcast_random_name.RandomNameContract
import timber.log.Timber

class RandomNameReceiver: BroadcastReceiver() {

	override fun onReceive(context: Context, intent: Intent) {
		Timber.d("onReceive")
		if (intent.action != RandomNameContract.ACTION) return

		val name = intent.getStringExtra(RandomNameContract.NAME_KEY) ?: RandomNameContract.NAME_DEFAULT
		Toast.makeText(context, "RandomNameReceiver $name", Toast.LENGTH_SHORT).show()
	}
}