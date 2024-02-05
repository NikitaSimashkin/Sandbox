package ru.kram.provider.presentation.updateuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.launch
import ru.kram.provider.domain.UserRepository
import ru.kram.provider.presentation.mapper.UserToUserUiMapper
import ru.kram.provider.presentation.mapper.UserUiToUserMapper
import ru.kram.provider.presentation.model.UserUi

class UpdateUserViewModel(
	private val userRepository: UserRepository,
	private val userToUserUiMapper: UserToUserUiMapper,
	private val userUiToUserMapper: UserUiToUserMapper
): ViewModel() {

	private val internalUserFlow = MutableStateFlow<UserUi?>(null)
	val userFlow: Flow<UserUi?> = internalUserFlow.dropWhile { it == null  }
	private var currentVisibleUser: UserUi? = null

	fun getUser(id: Long) {
		viewModelScope.launch(Dispatchers.IO) {
			if (currentVisibleUser != null) {
				internalUserFlow.value = currentVisibleUser
				return@launch
			}

			val user = userRepository.getUser(id)
			if (user != null) {
				val user = userToUserUiMapper(user)
				internalUserFlow.value = user
				currentVisibleUser = user
			}
		}
	}

	fun saveCurrentVisibleUser(user: UserUi) {
		currentVisibleUser = user
	}

	fun updateUser(user: UserUi) {
		viewModelScope.launch(Dispatchers.IO) {
			userRepository.updateUser(userUiToUserMapper(user))
		}
	}

	companion object {
		private const val TAG = "UpdateUserViewModel"
	}
}