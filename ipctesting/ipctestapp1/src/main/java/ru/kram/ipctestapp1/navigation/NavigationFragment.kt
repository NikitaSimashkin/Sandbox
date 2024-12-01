package ru.kram.ipctestapp1.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.ipctestapp1.R
import ru.kram.ipctestapp1.broadcast.RandomNameFragment
import ru.kram.ipctestapp1.databinding.FragmentNavigationBinding
import ru.kram.ipctestapp1.services.ipctestapp1ServiceFragment

class NavigationFragment: Fragment(R.layout.fragment_navigation) {

	private val binding by viewBinding(FragmentNavigationBinding::bind)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
		services.setOnClickListener {
			openFragment(ipctestapp1ServiceFragment())
		}
		broadcast.setOnClickListener {
			openFragment(RandomNameFragment())
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