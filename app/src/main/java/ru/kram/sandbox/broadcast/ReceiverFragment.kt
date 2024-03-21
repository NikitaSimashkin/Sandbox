package ru.kram.sandbox.broadcast

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.broadcast.random_name.GoAsyncContract
import ru.kram.broadcast.random_name.PeekServiceContract
import ru.kram.broadcast.random_name.RandomNameNonManifestContract
import ru.kram.deathstar.deathstar_contract.DeathStar
import ru.kram.deathstar.deathstar_contract.DeathStarServiceContract
import ru.kram.deathstar.deathstar_contract.aidl.IDeathStarService
import ru.kram.sandbox.R
import ru.kram.sandbox.broadcast.NumReceiver.Companion.NUM_KEY
import ru.kram.sandbox.databinding.FragmentReceiverBinding
import java.lang.ref.WeakReference
import kotlin.concurrent.thread

class ReceiverFragment : Fragment(R.layout.fragment_receiver) {

	private val binding by viewBinding(FragmentReceiverBinding::bind)

	private var nonManifestReceiver: BroadcastReceiver? = null
	private var goAsyncReceiver: BroadcastReceiver? = null
	private var peekReceiver: BroadcastReceiver? = null

	private var receiver1: BroadcastReceiver? = null
	private var receiver2: BroadcastReceiver? = null

	@SuppressLint("UnspecifiedRegisterReceiverFlag")
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.registerNonManifestReceiverButton.setOnClickListener {
			nonManifestReceiver = RandomNameNonManifestReceiver()
			requireContext().registerReceiver(
				nonManifestReceiver,
				RandomNameNonManifestContract.getReceiveIntentFilter(),
				RandomNameNonManifestContract.SEND_PERMISSION,
				null
			)
		}

		binding.unregisterNonManifestReceiverButton.setOnClickListener {
			requireContext().unregisterReceiver(nonManifestReceiver)
			nonManifestReceiver = null
		}

		binding.registerGoAsyncReceiverButton.setOnClickListener {
			Log.d(TAG, "registerGoAsyncReceiver")
			goAsyncReceiver = GoAsyncReceiver(binding)
			requireContext().registerReceiver(
				goAsyncReceiver,
				GoAsyncContract.getReceiveIntentFilter(),
				null,
				null
			)
		}

		binding.unregisterGoAsyncReceiverButton.setOnClickListener {
			requireContext().unregisterReceiver(goAsyncReceiver)
			goAsyncReceiver = null
		}

		binding.registerPeekReceiverButton.setOnClickListener {
			Log.d(TAG, "registerPeekReceiver")
			peekReceiver = PeekServiceReceiver(binding)
			requireContext().registerReceiver(
				peekReceiver,
				PeekServiceContract.getReceiverIntentFilter(),
				null,
				null
			)
		}

		binding.unregisterPeekReceiverButton.setOnClickListener {
			requireContext().unregisterReceiver(peekReceiver)
			peekReceiver = null
		}

		var counter = 0
		binding.registerNumsReceiverButton.setOnClickListener {
			if (receiver1 != null) {
				requireContext().unregisterReceiver(receiver1)
			}
			if (receiver2 != null) {
				requireContext().unregisterReceiver(receiver2)
			}
			binding.goAsyncResult.text = ""
			Log.d(TAG, "counter=$counter")
			receiver1 = NumReceiver.create("first", binding)
			receiver2 = NumReceiver.create("second", binding)

			val filter1 = IntentFilter(NumReceiver.ACTION)
			filter1.priority = counter % 2

			val filter2 = IntentFilter(NumReceiver.ACTION)
			filter2.priority = (counter + 1) % 2

			requireContext().registerReceiver(receiver1, filter1)
			requireContext().registerReceiver(receiver2, filter2)

			counter++
		}

		binding.unregisterNumsReceiverButton.setOnClickListener {
			requireContext().unregisterReceiver(receiver1)
			requireContext().unregisterReceiver(receiver2)
		}

		binding.sendNumsIntentButton.setOnClickListener {
			val intent = Intent(NumReceiver.ACTION)
			val resultReceiver = object : BroadcastReceiver() {
				override fun onReceive(context: Context?, intent: Intent?) {
					Log.d("ResultReceiver", "onReceive result")
					val res = goAsync()
					thread {
						Thread.sleep(5000)
						Handler(Looper.getMainLooper()).post {
							binding.goAsyncResult.text = "${res.getResultExtras(true).getInt(NUM_KEY)} resultReceiver"
						}
						res.finish()
					}
				}
			}
			requireContext().sendOrderedBroadcast(
				intent,
				null,
				resultReceiver,
				null,
				0,
				null,
				bundleOf(NUM_KEY to 15),
			)
		}
	}

	class RandomNameNonManifestReceiver : BroadcastReceiver() {
		override fun onReceive(context: Context, intent: Intent) {
			Log.d(TAG, "onReceive")
			if (intent.action != RandomNameNonManifestContract.ACTION) return

			val name = intent.getStringExtra(RandomNameNonManifestContract.NAME_KEY)
				?: RandomNameNonManifestContract.NAME_DEFAULT
			Toast.makeText(context, "$TAG $name", Toast.LENGTH_SHORT).show()
		}

		companion object {
			private const val TAG = "RandomNameNonManifestReceiver"
		}
	}

	class GoAsyncReceiver(binding: FragmentReceiverBinding) : BroadcastReceiver() {
		private val bindingRef = WeakReference(binding)
		override fun onReceive(context: Context, intent: Intent) {
			Log.d(TAG, "onReceive")
			val pendingResult = goAsync()
			thread {
				for (i in 0..120) {
					Thread.sleep(1000)
					Log.d(TAG, "$i")
					bindingRef.get()?.root?.post {
						bindingRef.get()?.goAsyncResult?.text = i.toString()
					}
				}
				Log.d(TAG, "finish")
				pendingResult.finish()
			}
		}

		companion object {
			private const val TAG = "GoAsyncReceiver"
		}
	}

	class PeekServiceReceiver(binding: FragmentReceiverBinding) : BroadcastReceiver() {
		private val bindingRef = WeakReference(binding)
		override fun onReceive(context: Context, intent: Intent) {
			Log.d(TAG, "onReceive")
			val binder = peekService(context, DeathStarServiceContract.getDeathStarServiceIntent())
			val service = IDeathStarService.Stub.asInterface(binder)
			val deathStar = DeathStar()
			service.fillDeathStarSync(deathStar)
			bindingRef.get()?.root?.post {
				bindingRef.get()?.goAsyncResult?.text = deathStar.toString()
			}
		}

		companion object {
			private const val TAG = "PeekServiceReceiver"
		}
	}

	companion object {
		private const val TAG = "ReceiverFragment"
	}
}