package ru.kram.sandbox.viewpager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.sandbox.databinding.FragmentViewpagerBinding

class ViewPagerFragment: Fragment() {

	private val binding by viewBinding(FragmentViewpagerBinding::bind)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

	}
}