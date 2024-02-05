package ru.kram.provider.presentation.adduser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kram.provider.domain.UserRepository
import ru.kram.provider.presentation.mapper.UserToUserUiMapper
import ru.kram.provider.presentation.mapper.UserUiToUserMapper

class AddUserViewModelFactory(
	private val repository: UserRepository,
	private val userUiToUserMapper: UserUiToUserMapper
): ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return AddUserViewModel(repository, userUiToUserMapper) as T
	}
}