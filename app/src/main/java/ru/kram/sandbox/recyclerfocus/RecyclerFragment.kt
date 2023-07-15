package ru.kram.sandbox.recyclerfocus

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.sandbox.R
import ru.kram.sandbox.databinding.FragmentRecyclerFocusBinding

class RecyclerFragment: Fragment(R.layout.fragment_recycler_focus) {

	private val personRepository = PersonRepository()
	private val personAdapter = PersonAdapter()

	private val binding by viewBinding(FragmentRecyclerFocusBinding::bind)

	private val number: Int get() = binding.text.text.toString().toInt()

	override fun onStart() {
		super.onStart()
		requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
	}

	override fun onStop() {
		super.onStop()
		requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		setUpRecycler()
	}

	private fun setUpRecycler() = with(binding) {
		recyclerView.apply {
			layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
			adapter = personAdapter
			personAdapter.submitList(personRepository.getPersons())
		}

		text.text = "1"

		scroll.setOnClickListener {
			recyclerView.scrollToPosition(number)
		}

		smoothScroll.setOnClickListener {
			recyclerView.smoothScrollToPosition(number)
		}

		left.setOnClickListener {
			text.text = (number - 1).toString()
		}

		right.setOnClickListener {
			text.text = (number + 1).toString()
		}

		requestFocus.setOnClickListener {
			recyclerView.getChildAt(number).requestFocus()
		}
	}
}