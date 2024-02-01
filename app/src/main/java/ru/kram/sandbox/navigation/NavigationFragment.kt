package ru.kram.sandbox.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.sandbox.R
import ru.kram.sandbox.add.AddFragment
import ru.kram.sandbox.carousel.CarouselFragment
import ru.kram.sandbox.carousel2.CarouselFragment2
import ru.kram.sandbox.databinding.FragmentNavigationBinding
import ru.kram.sandbox.player.PlayerFragment
import ru.kram.sandbox.recyclerfocus.RecyclerFragment
import ru.kram.sandbox.rx.RxFragment
import ru.kram.sandbox.service.ServiceFragment
import ru.kram.sandbox.textviewtest.TextViewTestFragment

class NavigationFragment: Fragment(R.layout.fragment_navigation) {

	private val binding by viewBinding(FragmentNavigationBinding::bind)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
		exoPlayerButton.setOnClickListener {
			openFragment(PlayerFragment())
		}
		addButton.setOnClickListener {
			openFragment(AddFragment())
		}
		recyclerFocusButton.setOnClickListener {
			openFragment(RecyclerFragment())
		}
		testTextviewButton.setOnClickListener {
			openFragment(TextViewTestFragment())
		}
		carouselButton.setOnClickListener {
			openFragment(CarouselFragment())
		}
		carousel2Button.setOnClickListener {
			openFragment(CarouselFragment2())
		}
		rxButton.setOnClickListener {
			openFragment(RxFragment())
		}
		serviceButton.setOnClickListener {
			openFragment(ServiceFragment())
		}
	}

	private fun openFragment(fragment: Fragment) {
		parentFragmentManager.beginTransaction().apply {
			addToBackStack(null)
			replace(R.id.main_container_fragment, fragment)
			commit()
		}
	}
}