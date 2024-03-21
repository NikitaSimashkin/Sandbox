package ru.kram.sandbox.biglist.presentation.stateholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kram.sandbox.biglist.data.UserRepositoryImpl
import ru.kram.sandbox.biglist.presentation.mapper.UserToUserUiMapper

val repository = UserRepositoryImpl()
private val mapper = UserToUserUiMapper()

class UserViewModelFactory: ViewModelProvider.Factory {

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return UserViewModel(repository, mapper) as T
	}
}