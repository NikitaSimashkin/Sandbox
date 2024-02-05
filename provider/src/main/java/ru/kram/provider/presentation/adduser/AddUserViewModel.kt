package ru.kram.provider.presentation.adduser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kram.provider.domain.UserRepository
import ru.kram.provider.presentation.mapper.UserUiToUserMapper
import ru.kram.provider.presentation.model.UserUi

class AddUserViewModel(
	private val userRepository: UserRepository,
	private val userUiToUserMapper: UserUiToUserMapper
): ViewModel() {

	fun addUser(user: UserUi) {
		viewModelScope.launch(Dispatchers.IO) {
			userRepository.addUser(userUiToUserMapper(user))
		}
	}
}