package ru.kram.sandbox.service

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kram.sandbox.R
import ru.kram.sandbox.databinding.FragmentServiceBinding
import ru.kram.common.service_contract.RandomMessengerServiceContract
import ru.kram.common.util.process.ProcessNameProvider
import ru.kram.deathstar.deathstar_contract.DeathStar
import ru.kram.deathstar.deathstar_contract.DeathStarServiceContract
import ru.kram.deathstar.deathstar_contract.aidl.IDeathStarResponseListener
import ru.kram.deathstar.deathstar_contract.aidl.IDeathStarService

class ServiceFragment : Fragment(R.layout.fragment_service) {

	private val binding: FragmentServiceBinding by viewBinding(FragmentServiceBinding::bind)

	private val requestPostNotificationLauncher =
		registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
			// nothing
		}

	private var receiveMessenger: Messenger? = null
	private var requestMessenger: Messenger? = null

	private var deathStarService: IDeathStarService? = null

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		binding.startForegroundService.setOnClickListener {
			if (!checkPostNotificationPermission()) {
				requestPostNotification()
			} else {
				requireContext().startService(
					Intent(
						requireContext(),
						CountForegroundService::class.java
					).apply {
						action = CountForegroundService.ACTION_START
					})
			}
		}
		binding.stopForegroundService.setOnClickListener {
			requireContext().startService(
				Intent(
					requireContext(),
					CountForegroundService::class.java
				).apply {
					action = CountForegroundService.ACTION_STOP
				})
		}
		binding.detachNotification.setOnClickListener {
			requireContext().startService(
				Intent(
					requireContext(),
					CountForegroundService::class.java
				).apply {
					action = CountForegroundService.ACTION_STOP_FOREGROUND_DETACH
				})
		}
		binding.removeNotification.setOnClickListener {
			requireContext().startService(
				Intent(
					requireContext(),
					CountForegroundService::class.java
				).apply {
					action = CountForegroundService.ACTION_STOP_FOREGROUND_REMOVE
				})
		}

		binding.startBackgroundService.setOnClickListener {
			requireContext().startService(
				Intent(
					requireContext(),
					CountBackgroundService::class.java
				)
			)
		}
		binding.stopBackgroundService.setOnClickListener {
			requireContext().stopService(
				Intent(
					requireContext(),
					CountBackgroundService::class.java
				)
			)
		}

		binding.startBackgroundInForeground.setOnClickListener {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				requireContext().startForegroundService(
					Intent(
						requireContext(),
						CountBackgroundService::class.java
					)
				)
			}
		}

		var lastNumber: Int? = null
		val randomNumberConnection = RandomNumberConnection(lifecycleScope) {
			withContext(Dispatchers.Main) {
				lastNumber = it
				binding.number.text = "$it binded"
			}
		}
		binding.bindServiceSameProcess.setOnClickListener {
			requireContext().bindService(
				Intent(requireContext(), RandomSameProcessService::class.java),
				randomNumberConnection,
				BIND_AUTO_CREATE
			)
		}
		binding.unbindServiceSameProcess.setOnClickListener {
			requireContext().unbindService(randomNumberConnection)
			binding.number.text = "$lastNumber unbinded"
		}
		binding.stopBindedServiceSameProcess.setOnClickListener {
			requireContext().stopService(
				Intent(
					requireContext(),
					RandomSameProcessService::class.java
				)
			)
		}

		val messengerConnection = RandomMessengerConnection {
			view.post {
				setMessengerText("binded $it")
			}
		}

		binding.bindMessengerService.setOnClickListener {
			requireContext().bindService(
				RandomMessengerServiceContract.getRandomMessengerServiceIntent(),
				messengerConnection,
				BIND_AUTO_CREATE
			)
			setMessengerText("binded")
		}
		binding.unbindMessengerService.setOnClickListener {
			requireContext().unbindService(messengerConnection)
			setMessengerText("unbinded")
		}
		binding.generateNewMessengerNumber.setOnClickListener {
			requestMessenger?.send(Message.obtain().apply {
				what = RandomMessengerServiceContract.GENERATE_NEW_NUMBER
			})
		}
		binding.getMessengerNumber.setOnClickListener {
			requestMessenger?.send(Message.obtain().apply {
				what = RandomMessengerServiceContract.REQUEST_NUMBER
				replyTo = receiveMessenger
			})
		}

		var isDeathStarBound = false
		lateinit var deathStarConnection: AidlServerConnection
		binding.aidlBind.setOnClickListener {
			isDeathStarBound = if (!isDeathStarBound) {
				deathStarConnection = AidlServerConnection()
				requireContext().bindService(
					DeathStarServiceContract.getDeathStarServiceIntent(),
					deathStarConnection,
					BIND_AUTO_CREATE
				)
				true
			} else {
				requireContext().unbindService(deathStarConnection)
				false
			}
		}
		binding.fillSyncOut.setOnClickListener {
			val star = DeathStar().apply {
				owner = "Nikita"
			}
			Log.d(TAG, "fillSyncOut, send $star")
			deathStarService?.fillDeathStarSync(star)
			Log.d(TAG, "fillSyncOut, result $star")
		}
		binding.fillAsyncIn.setOnClickListener {
			val listener = object: IDeathStarResponseListener.Stub() {
				override fun onResponse(deathStar: DeathStar) {
					Log.d(TAG, "fillAsyncIn, result $deathStar")
				}
			}
			deathStarService?.fillDeathStarAsync(listener)
		}
		binding.fillInOut.setOnClickListener {
			val star = DeathStar().apply {
				width = 1
				height = 2
				weight = 3
			}
			Log.d(TAG, "fillInOut, send $star")
			deathStarService?.inoutFillDeathStar(star)
			Log.d(TAG, "fillInOut, result $star")
		}
		binding.fillIn.setOnClickListener {
			val star = DeathStar().apply {
				width = 12
				height = 22
				weight = 32
			}
			Log.d(TAG, "fillIn, send $star")
			deathStarService?.fillDeathStarIn(star)
			Log.d(TAG, "fillIn, result $star")
		}

		binding.startActivityFromBackground.setOnClickListener {
			requireContext().startService(
				Intent(
					requireContext(),
					CountForegroundService::class.java
				).apply {
					action = CountForegroundService.ACTION_START_ACTIVITY
				})
		}
	}

	private fun setMessengerText(text: String) {
		binding.stateMessenger.text = text
	}

	private fun requestPostNotification() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			requestPostNotificationLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
		}
	}

	private fun checkPostNotificationPermission(): Boolean {
		return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			activity?.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
		} else {
			true
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		deathStarService = null
	}

	class RandomNumberConnection(
		private val scope: CoroutineScope,
		private val action: suspend (Int) -> Unit
	) : ServiceConnection {
		private val TAG = "RandomNumberConnection"

		private var numberJob: Job? = null

		override fun onServiceConnected(name: ComponentName, service: IBinder) {
			Log.d(TAG, "onServiceConnected")
			numberJob = scope.launch {
				(service as RandomSameProcessService.Binder).getNumberFlow().collect {
					action(it)
				}
			}
		}

		// if onBind returns null
		override fun onNullBinding(name: ComponentName?) {
			Log.d(TAG, "onNullBinding")
			numberJob?.cancel()
			super.onNullBinding(name)
		}

		// if binding died incorrect way (doesn't call if use unbindService)
		override fun onBindingDied(name: ComponentName) {
			Log.d(TAG, "onBindingDied")
			numberJob?.cancel()
			super.onBindingDied(name)
		}

		// onServiceDisconnected() would be called if the service crashed or was killed
		override fun onServiceDisconnected(name: ComponentName) {
			Log.d(TAG, "onServiceDisconnected")
			numberJob?.cancel()
		}
	}

	inner class RandomMessengerConnection(
		private val action: (Int) -> Unit
	) : ServiceConnection {
		private val TAG = "RandomMessengerConnection"

		override fun onServiceConnected(name: ComponentName, service: IBinder) {
			Log.d(TAG, "onServiceConnected")
			requestMessenger = Messenger(service)
			receiveMessenger = Messenger(createReceiveHandler())
		}

		private fun createReceiveHandler() = object : Handler(Looper.getMainLooper()) {
			override fun handleMessage(msg: Message) {
				when (msg.what) {
					RandomMessengerServiceContract.RECEIVE_NUMBER -> {
						Log.d(
							TAG,
							"RECEIVE_NUMBER, ${ProcessNameProvider.getThreadAndProcessInfo()}"
						)
						action(msg.arg1)
					}

					else -> super.handleMessage(msg)
				}
			}
		}

		override fun onNullBinding(name: ComponentName?) {
			Log.d(TAG, "onNullBinding")
			super.onNullBinding(name)
		}

		override fun onBindingDied(name: ComponentName) {
			finish()
			Log.d(TAG, "onBindingDied")
			super.onBindingDied(name)
		}

		override fun onServiceDisconnected(name: ComponentName) {
			finish()
			Log.d(TAG, "onServiceDisconnected")
		}

		private fun finish() {
			receiveMessenger = null
			requestMessenger = null
		}
	}

	inner class AidlServerConnection : ServiceConnection {
		private val TAG = "AidlServerConnection"

		override fun onServiceConnected(name: ComponentName, service: IBinder) {
			deathStarService = IDeathStarService.Stub.asInterface(service)
			Log.d(TAG, "onServiceConnected")
		}

		override fun onNullBinding(name: ComponentName?) {
			Log.d(TAG, "onNullBinding")
			super.onNullBinding(name)
		}

		override fun onBindingDied(name: ComponentName) {
			Log.d(TAG, "onBindingDied")
			super.onBindingDied(name)
		}

		override fun onServiceDisconnected(name: ComponentName) {
			Log.d(TAG, "onServiceDisconnected")
		}
	}

	companion object {
		private const val TAG = "ServiceFragment"
	}
}