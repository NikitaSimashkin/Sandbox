package ru.kram.boxsand.services

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.boxsand.R
import ru.kram.boxsand.databinding.FragmentServiceBinding
import ru.kram.common.service_contract.RandomMessengerServiceContract
import ru.kram.common.util.process.ProcessNameProvider

class BoxsandServiceFragment : Fragment(R.layout.fragment_service) {

	private val binding: FragmentServiceBinding by viewBinding(FragmentServiceBinding::bind)

	private var receiveMessenger: Messenger? = null
	private var requestMessenger: Messenger? = null

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
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
	}

	private fun setMessengerText(text: String) {
		binding.stateMessenger.text = text
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
						Log.d(TAG, "RECEIVE_NUMBER, ${ProcessNameProvider.getThreadAndProcessInfo()}")
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

	companion object {
		private const val TAG = "BoxsandServiceFragment"
	}
}