package ru.kram.sandbox.edittext

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.sandbox.R
import ru.kram.sandbox.databinding.FragmentEditTextBinding

class EditTextFragment: Fragment(R.layout.fragment_edit_text) {
	private val binding by viewBinding(FragmentEditTextBinding::bind)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
	}
}