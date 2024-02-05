package ru.kram.provider.presentation.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kram.provider.domain.UserRepository
import ru.kram.provider.presentation.mapper.UserToUserUiMapper
import ru.kram.provider.presentation.mapper.UserUiToUserMapper

class UserlistViewModelFactory(
	private val repository: UserRepository,
	private val userToUserUiMapper: UserToUserUiMapper
): ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return UserlistViewModel(repository, userToUserUiMapper) as T
	}
}