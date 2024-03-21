package ru.kram.boxsand.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.boxsand.R
import ru.kram.boxsand.broadcast.RandomNameFragment
import ru.kram.boxsand.databinding.FragmentNavigationBinding
import ru.kram.boxsand.services.BoxsandServiceFragment

class NavigationFragment: Fragment(R.layout.fragment_navigation) {

	private val binding by viewBinding(FragmentNavigationBinding::bind)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
		services.setOnClickListener {
			openFragment(BoxsandServiceFragment())
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