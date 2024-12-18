package ru.kram.sandbox.features.biglist.presentation

import android.os.Bundle
import android.view.Choreographer
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import ru.kram.sandbox.common.koin.injectUnsafe
import ru.kram.sandbox.features.biglist.R
import ru.kram.sandbox.features.biglist.databinding.FragmentBigListBinding
import ru.kram.sandbox.features.biglist.presentation.stateholder.UserViewModel
import timber.log.Timber

class BigListFragment: Fragment(R.layout.fragment_big_list) {

	private val binding by viewBinding(FragmentBigListBinding::bind)

	private val viewModel: UserViewModel by injectUnsafe()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		val adapter = UserListAdapter {
			viewModel.openRandomFragmentOnTop(requireActivity())
		}
		adapter.setHasStableIds(true) // optimization

		Choreographer.getInstance().postFrameCallback(createChoreographerFrameListener())
		// adb shell dumpsys gfxinfo -t 10 ru.kram.sandbox > gfxinfo.txt
		// time in seconds

		// adb shell dumpsys gfxinfo ru.kram.sandbox reset

		// adb shell input touchscreen swipe 200 1000 500 700 100
		// auto scroll

		with(binding) {
			rvBigList.swapAdapter(adapter, false) // optimization
			rvBigList.setHasFixedSize(false) // if true - optimization
			rvBigList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

			val pool = rvBigList.recycledViewPool
			pool.setMaxRecycledViews(0, 10) // optimization
			rvBigList.setRecycledViewPool(pool) // can share view pool if has same view holders, optimization
		}

		viewLifecycleOwner.lifecycleScope.launch {
			viewModel.startLoadUsers()
			viewModel.getItems().collect { users ->
				adapter.submitList(users)
			}
		}
	}

	override fun onDestroyView() {
		viewModel.stopLoadUsers()
		super.onDestroyView()
	}

	private fun createChoreographerFrameListener(): Choreographer.FrameCallback {
		return object : Choreographer.FrameCallback {
			private var lastFrameTimeNanos: Long = 0

			override fun doFrame(frameTimeNanos: Long) {
				val frameIntervalNanos = if (lastFrameTimeNanos == 0L) 0 else frameTimeNanos - lastFrameTimeNanos
				lastFrameTimeNanos = frameTimeNanos
				val frameIntervalMs = frameIntervalNanos / 1_000_000
				if (frameIntervalMs > 30) {
                    Timber.d("doFrame: $frameIntervalMs")
				}
				if (isAdded) {
					Choreographer.getInstance().postFrameCallback(this)
				}
			}
		}
	}
}