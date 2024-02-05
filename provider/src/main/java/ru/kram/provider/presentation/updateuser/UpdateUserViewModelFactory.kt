package ru.kram.provider.presentation.updateuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kram.provider.domain.UserRepository
import ru.kram.provider.presentation.mapper.UserToUserUiMapper
import ru.kram.provider.presentation.mapper.UserUiToUserMapper

class UpdateUserViewModelFactory(
	private val repository: UserRepository,
	private val userToUserUiMapper: UserToUserUiMapper,
	private val userUiToUserMapper: UserUiToUserMapper
): ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return UpdateUserViewModel(repository, userToUserUiMapper, userUiToUserMapper) as T
	}
}