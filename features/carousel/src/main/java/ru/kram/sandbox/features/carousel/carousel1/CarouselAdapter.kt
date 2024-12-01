package ru.kram.sandbox.features.carousel.carousel1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.kram.sandbox.features.carousel.databinding.ItemCarouselBinding
import timber.log.Timber

internal class CarouselAdapter: ListAdapter<String, CarouselHolder>(CarouselDiffCallback()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselHolder {
		Timber.d("onCreateViewHolder")
		val view = ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return CarouselHolder(view.root)
	}

	override fun onBindViewHolder(holder: CarouselHolder, position: Int) {
		Timber.d("onBindViewHolder, position = $position")
		val realPosition = position % currentList.size
		holder.bind(currentList[realPosition])
	}

	override fun getItemCount(): Int {
		return Int.MAX_VALUE
	}

	companion object {
		private const val SCALE_MIN = 0.7f
		private const val SCALE_MAX = 1f
	}
}