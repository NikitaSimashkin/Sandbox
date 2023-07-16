package ru.kram.sandbox.carousel

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSmoothScroller
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.sandbox.R
import ru.kram.sandbox.databinding.FragmentCarouselBinding

class CarouselFragment: Fragment(R.layout.fragment_carousel) {

	private val binding by viewBinding(FragmentCarouselBinding::bind)
	private val mainHandler = Handler(Looper.getMainLooper())

	private var centerPosition = Int.MAX_VALUE / 2

	private val scrollThread = object : Thread() {
		override fun run() {
			while (!isInterrupted) {
				try {
					sleep(4000)
					centerPosition++
					mainHandler.post { smoothScrollToPosition(centerPosition) }
				} catch (e: InterruptedException) {
					break
				}
			}
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		setUpRecycler()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		scrollThread.interrupt()
	}

	private fun setUpRecycler() = with(binding) {
		carouselRecycler.apply {
			layoutManager = SliderLayoutManager(requireContext())
			adapter = CarouselAdapter().apply {
				submitList(listOf("Рома", "Никита", "Дима"))
			}
			layoutManager?.apply {
				scrollToPosition(centerPosition)
			}
			scrollThread.start()
		}
	}

	fun smoothScrollToPosition(position: Int) {
		val smoothScroller = object : LinearSmoothScroller(requireContext()) {
			override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
				return 3f
			}
		}
		smoothScroller.targetPosition = position
		binding.carouselRecycler.layoutManager?.startSmoothScroll(smoothScroller)
	}

	companion object {
		private const val TAG = "CarouselFragment"
	}
}
