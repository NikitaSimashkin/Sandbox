package ru.kram.sandbox.features.carousel.carousel2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kram.sandbox.features.carousel.R
import ru.kram.sandbox.features.carousel.databinding.FragmentCarousel2Binding

class CarouselFragment2: Fragment(R.layout.fragment_carousel2) {

	private val binding by viewBinding(FragmentCarousel2Binding::bind)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
			while(lifecycleScope.isActive) {
				delay(2800L)
				withContext(Dispatchers.Main) {
					binding.carouselView.scroll(randomStrings.random())
				}
			}
		}
	}

	companion object {
		private val randomStrings = listOf(
			"Майор Гром",
			"Лучшие фильмы 22 года",
			"комедии"
		)
	}
}