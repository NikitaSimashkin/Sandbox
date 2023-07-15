package ru.kram.sandbox.carousel

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.kram.sandbox.databinding.CarouselItemBinding

class CarouselAdapter: ListAdapter<String, CarouselHolder>(CarouselDiffCallback()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselHolder {
		Log.d(TAG, "onCreateViewHolder")
		val view = CarouselItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return CarouselHolder(view.root)
	}

	override fun onBindViewHolder(holder: CarouselHolder, position: Int) {
		Log.d(TAG, "onBindViewHolder, position = $position")
		val realPosition = position % currentList.size
		holder.bind(currentList[realPosition])
	}

	override fun getItemCount(): Int {
		return Int.MAX_VALUE
	}

	companion object {
		private const val TAG = "CarouselAdapter"
		private const val SCALE_MIN = 0.7f
		private const val SCALE_MAX = 1f
	}
}