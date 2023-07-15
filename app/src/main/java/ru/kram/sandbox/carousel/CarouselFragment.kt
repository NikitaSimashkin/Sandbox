package ru.kram.sandbox.carousel

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import ru.kram.sandbox.databinding.CarouselFragmentBinding

class CarouselFragment: Fragment() {

	private var _binding: CarouselFragmentBinding? = null
	private val binding get() = _binding!!

	private var centerPosition = Int.MAX_VALUE / 2

	private val scrollThread = object : Thread() {
		override fun run() {
			while (!isInterrupted) {
				sleep(4000)
				centerPosition++
				smoothScrollToPosition(centerPosition)
			}
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = CarouselFragmentBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
		carouselRecycler.apply {
			layoutManager = SliderLayoutManager(requireContext())
			adapter = CarouselAdapter().apply {
				submitList(listOf("Рома", "Никита", "Дима"))
			}
			layoutManager?.apply {
				scrollToPosition(centerPosition)
			}
			scrollThread.start()
			addOnScrollListener(object: OnScrollListener() {
				override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
					super.onScrolled(recyclerView, dx, dy)
					Log.d(TAG, "$dx $dy")
				}
			})
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

	override fun onDestroyView() {
		super.onDestroyView()
		scrollThread.interrupt()
	}

	companion object {
		private const val TAG = "CarouselFragment"
	}

}