package ru.kram.provider.presentation.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.kram.provider.domain.UserRepository
import ru.kram.provider.presentation.model.UserUi
import ru.kram.provider.presentation.mapper.UserToUserUiMapper

class UserlistViewModel(
	private val repository: UserRepository,
	private val userToUserUiMapper: UserToUserUiMapper
): ViewModel() {

	private val internalUsers = MutableStateFlow<List<UserUi>>(emptyList())
	val users: StateFlow<List<UserUi>> = internalUsers

	fun collectUsers() {
		viewModelScope.launch(Dispatchers.IO) {
			repository.getAllUsers().collect {
				internalUsers.value = it.map(userToUserUiMapper)
			}
		}
	}

	fun removeUser(id: Long) {
		viewModelScope.launch(Dispatchers.IO) {
			repository.deleteUser(id)
		}
	}
}