package ru.kram.sandbox.recyclerfocus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.kram.sandbox.databinding.FragmentRecyclerFocusBinding

class RecyclerFragment: Fragment() {

	private val personRepository = PersonRepository()
	private val personAdapter = PersonAdapter()

	private var _binding: FragmentRecyclerFocusBinding? = null
	private val binding get() = _binding!!

	private val number: Int get() = binding.text.text.toString().toInt()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentRecyclerFocusBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		setUpRecycler()
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
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