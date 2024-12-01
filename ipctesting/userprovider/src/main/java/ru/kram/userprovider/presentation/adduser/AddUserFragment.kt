package ru.kram.userprovider.presentation.adduser

import android.os.Bundle
import android.view.View
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.get
import org.koin.androidx.scope.ScopeFragment
import org.koin.core.context.GlobalContext
import ru.kram.userprovider.R
import ru.kram.userprovider.databinding.FragmentAddUserBinding
import ru.kram.userprovider.presentation.model.UserUi
import ru.kram.userprovider.presentation.navigation.DatabaseRouter

class AddUserFragment: ScopeFragment(R.layout.fragment_add_user) {

	private val binding: FragmentAddUserBinding by viewBinding(FragmentAddUserBinding::bind)
	private val router by lazy { requireActivity().get<DatabaseRouter>() }
	private val viewModel by viewModels<AddUserViewModel> { GlobalContext.get().get<AddUserViewModelFactory>() }

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		setUpAddUserButton()
	}

	private fun setUpAddUserButton() {
		binding.addUser.setOnClickListener {
			val id = binding.id.text.toString()
			val name = binding.name.text.toString()
			val surname = binding.surname.text.toString()
			val age = binding.age.text.toString()
			val height = binding.height.text.toString()
			val weight = binding.weight.text.toString()
			val footSize = binding.footsize.text.toString()
			if (
				id.isNotEmpty() &&
				name.isNotEmpty() &&
				surname.isNotEmpty() &&
				age.isNotEmpty() &&
				height.isNotEmpty() &&
				weight.isNotEmpty() &&
				footSize.isNotEmpty() &&
				age.isDigitsOnly() &&
				height.isDigitsOnly() &&
				weight.isDigitsOnly() &&
				footSize.isDigitsOnly()
			) {
				viewModel.addUser(
					UserUi(
						id.toLong(),
						name,
						surname,
						age.toInt(),
						height.toInt(),
						weight.toInt(),
						footSize.toInt()
					)
				)
				router.navigateBack()
			}
		}
	}
}