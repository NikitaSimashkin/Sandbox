package ru.kram.userprovider.presentation.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kram.userprovider.domain.UserRepository
import ru.kram.userprovider.presentation.mapper.UserToUserUiMapper

class UserlistViewModelFactory(
	private val repository: UserRepository,
	private val userToUserUiMapper: UserToUserUiMapper
): ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return UserlistViewModel(repository, userToUserUiMapper) as T
	}
}