package ru.kram.sandbox.features.drawservice

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.sandbox.features.drawservice.databinding.FragmentDrawServiceBinding
import timber.log.Timber


class DrawServiceFragment: Fragment(R.layout.fragment_draw_service) {

	private val binding by viewBinding(FragmentDrawServiceBinding::bind)

	private var drawServiceConnection: ServiceConnection? = null
	private var drawBinder: DrawBinder? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
		intent.setData(Uri.parse("package:" + requireContext().packageName))
		startActivityForResult(intent, 0)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.startDrawServiceButton.setOnClickListener {
			bindDrawService()
		}

		binding.stopDrawServiceButton.setOnClickListener {
			unbindDrawService()
		}

		binding.drawRedCircleButton.setOnClickListener {
			drawBinder?.drawRedCircle()
		}

		binding.hideRedCircleButton.setOnClickListener {
			drawBinder?.hideRedCircle()
		}

		binding.drawBigRedCircleButton.setOnClickListener {
			drawBinder?.drawBigRedCircle()
		}
	}

	private fun bindDrawService() {
		drawServiceConnection = DrawServiceConnection()
		requireContext().bindService(
			Intent(requireContext(), DrawService::class.java),
			requireNotNull(drawServiceConnection),
			BIND_AUTO_CREATE
		)
	}

	private fun unbindDrawService() {
		drawServiceConnection?.let { requireContext().unbindService(it) }
		drawServiceConnection = null
		drawBinder = null
	}

	override fun onDestroyView() {
		super.onDestroyView()
		unbindDrawService()
	}

	private inner class DrawServiceConnection: ServiceConnection {

		override fun onServiceConnected(name: ComponentName, service: IBinder) {
			Timber.d("onServiceConnected")
			drawBinder = service as DrawBinder
		}

		override fun onServiceDisconnected(name: ComponentName) {
			Timber.d("onServiceDisconnected")
			drawBinder = null
		}
	}
}