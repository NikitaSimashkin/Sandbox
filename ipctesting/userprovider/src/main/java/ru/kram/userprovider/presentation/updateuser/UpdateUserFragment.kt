package ru.kram.userprovider.presentation.updateuser

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.text.isDigitsOnly
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.scope.ScopeFragment
import org.koin.core.context.GlobalContext
import ru.kram.userprovider.R
import ru.kram.userprovider.databinding.FragmentUpdateUserBinding
import ru.kram.userprovider.presentation.model.UserUi
import ru.kram.userprovider.presentation.navigation.DatabaseRouter
import timber.log.Timber

class UpdateUserFragment: ScopeFragment(R.layout.fragment_update_user) {

	private val binding: FragmentUpdateUserBinding by viewBinding(FragmentUpdateUserBinding::bind)
	private val router by lazy { requireActivity().get<DatabaseRouter>() }
	private val viewModel by viewModels<UpdateUserViewModel> { GlobalContext.get().get<UpdateUserViewModelFactory>() }

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		val userId = requireArguments().getLong(USER_ID)
		binding.root.isVisible = false
		viewModel.getUser(userId)
		lifecycleScope.launch {
			viewModel.userFlow.collect {
				if (it != null) {
					setUser(it)
				} else {
					setError()
				}
			}
		}

		binding.updateUserButton.setOnClickListener {
			updateUser()
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		viewModel.saveCurrentVisibleUser(createUser() ?: return)
	}

	private fun setUser(user: UserUi) {
		Timber.d("setUser $user")
		with(binding) {
			id.setText(user.id.toString())
			name.setText(user.name)
			surname.setText(user.surname)
			age.setText(user.age.toString())
			height.setText(user.height.toString())
			weight.setText(user.weight.toString())
			footsize.setText(user.footSize.toString())
			root.isVisible = true
		}
	}

	private fun setError() {
		Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
		router.navigateBack()
	}

	private fun updateUser() {
		val user = createUser()
		if (user != null) {
			viewModel.updateUser(user)
			router.navigateBack()
		}
	}

	private fun createUser(): UserUi? = with(binding) {
		val id = id.text.toString()
		val name = name.text.toString()
		val surname = surname.text.toString()
		val age = age.text.toString()
		val height = height.text.toString()
		val weight = weight.text.toString()
		val footSize = footsize.text.toString()

		if (
			id.isNotEmpty() &&
			name.isNotEmpty() ||
			surname.isNotEmpty() ||
			age.isNotEmpty() ||
			height.isNotEmpty() &&
			weight.isNotEmpty() &&
			footSize.isNotEmpty() &&
			age.isDigitsOnly() &&
			height.isDigitsOnly() &&
			weight.isDigitsOnly() &&
			footSize.isDigitsOnly()
		) {
			val user = UserUi(
				id.toLong(),
				name,
				surname,
				age.toInt(),
				height.toInt(),
				weight.toInt(),
				footSize.toInt()
			)
			user
		} else {
			/**
			 * Impossible case
			 */
			null
		}
	}

	companion object {
		private const val TAG = "UpdateUserFragment"
		private const val USER_ID = "user_id"

		fun create(userId: Long): UpdateUserFragment {
			return UpdateUserFragment().apply {
				arguments = bundleOf(
					USER_ID to userId
				)
			}
		}
	}
}