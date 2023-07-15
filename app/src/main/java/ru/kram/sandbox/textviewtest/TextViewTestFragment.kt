package ru.kram.sandbox.textviewtest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.sandbox.R
import ru.kram.sandbox.databinding.FragmentTextviewTestBinding

class TextViewTestFragment: Fragment(R.layout.fragment_textview_test) {

	private val binding by viewBinding(FragmentTextviewTestBinding::bind)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		startTest()
	}

	private fun startTest() {
		val str = "123"
		delayChangeString(str, 3000)
		delayChangeString(str + str, 6000)
		delayChangeString(str + str, 9000)
	}

	private fun delayChangeString(string: String, delay: Long) {
		with(binding.appcompatTextview) {
			postDelayed(
				{ text = string },
				delay
			)
		}
	}
}