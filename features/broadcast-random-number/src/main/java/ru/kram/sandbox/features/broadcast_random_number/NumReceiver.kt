package ru.kram.sandbox.features.broadcast_random_number

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.core.os.bundleOf
import ru.kram.sandbox.features.broadcast_random_number.databinding.FragmentReceiverBinding
import timber.log.Timber
import java.lang.ref.WeakReference
import kotlin.concurrent.thread

class NumReceiver(
	private val name: String,
	binding: FragmentReceiverBinding
) : BroadcastReceiver() {

	private val bindingRef = WeakReference(binding)

	override fun onReceive(context: Context, intent: Intent) {
		val value = getResultExtras(true).getInt(NUM_KEY, (0..100).random())
		val newValue = (0..100).random()
		Timber.d("onReceive, num=$name, value=$value, newValue=$newValue")
		val result = value + newValue
		val pendingResult = goAsync()
		thread {
			Thread.sleep(10000)
			pendingResult.setResultExtras(bundleOf(NUM_KEY to result))
			Handler(Looper.getMainLooper()).post {
				bindingRef.get()?.let {
					it.goAsyncResult.text = "$value $newValue $name"
				}
			}
			pendingResult.finish()
		}
	}

	companion object {
		private const val TAG = "RandomNameReceiver"
		const val NUM_KEY = "num"

		const val ACTION = "ru.kram.broadcast.action.num"


		fun create(name: String, binding: FragmentReceiverBinding): NumReceiver {
			val receiver = NumReceiver(name, binding)
			return receiver
		}
	}
}