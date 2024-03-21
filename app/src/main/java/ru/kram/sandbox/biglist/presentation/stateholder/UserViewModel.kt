package ru.kram.sandbox.biglist.presentation.stateholder

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import ru.kram.sandbox.biglist.domain.UserRepository
import ru.kram.sandbox.biglist.presentation.mapper.UserToUserUiMapper

class UserViewModel(
	private val repository: UserRepository,
	private val mapper: UserToUserUiMapper
): ViewModel() {

	val users = repository.getUsersFlow().map { it.map(mapper) }

	fun startLoadUsers() {
		repository.startLoadUsers()
	}

	fun stopLoadUsers() {
		repository.stopLoadUsers()
	}
}