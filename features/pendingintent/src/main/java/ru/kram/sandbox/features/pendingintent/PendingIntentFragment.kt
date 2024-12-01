package ru.kram.sandbox.features.pendingintent

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.sandbox.common.koin.injectUnsafe
import ru.kram.sandbox.features.pendingintent.databinding.FragmentPendingIntentBinding

class PendingIntentFragment: Fragment(R.layout.fragment_pending_intent) {

	private val binding by viewBinding(FragmentPendingIntentBinding::bind)

	private val activityComponentNameProvider: ActivityComponentNameProvider by injectUnsafe()

	private val requestPostNotificationLauncher =
		registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			requestPostNotificationLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
		}
	}

	@SuppressLint("MissingPermission")
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		binding.sendIntent.setOnClickListener {
			val pendingIntent = createPendingIntent(100)
			val notification = createNotification(pendingIntent)
			with(NotificationManagerCompat.from(requireContext())) {
				notify(1, notification)
			}
		}

		binding.updateIntent.setOnClickListener {
			createPendingIntent(20)
		}

		binding.cancelIntent.setOnClickListener {
			createPendingIntent(45).cancel()
		}
	}

	private fun createNotification(pendingIntent: PendingIntent): Notification {
		val builder: NotificationCompat.Builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
			.setSmallIcon(R.drawable.nikita)
			.setContentTitle("Pending intent")
			.setContentText("Hello, world")
			.setContentIntent(pendingIntent)
			.setAutoCancel(true)
			.setPriority(NotificationCompat.PRIORITY_DEFAULT)
		return builder.build()
	}

	private fun createPendingIntent(value: Int): PendingIntent {
		return PendingIntent.getActivity(
			requireContext(),
			REQUEST_CODE,
			Intent()
				.putExtra(VALUE_KEY, value)
				.setComponent(activityComponentNameProvider.get()),
			PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
		)
	}

	companion object {
		private const val TAG = "PendingIntentFragment"

		private const val REQUEST_CODE = 0

		const val VALUE_KEY = "value"
		const val CHANNEL_ID = "pending_intent_channel_id"
		const val CHANNEL_NAME = "pending_intent_channel_name"
	}
}