package ru.kram.userprovider.presentation.adduser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kram.userprovider.domain.UserRepository
import ru.kram.userprovider.presentation.mapper.UserUiToUserMapper

class AddUserViewModelFactory(
	private val repository: UserRepository,
	private val userUiToUserMapper: UserUiToUserMapper
): ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return AddUserViewModel(repository, userUiToUserMapper) as T
	}
}