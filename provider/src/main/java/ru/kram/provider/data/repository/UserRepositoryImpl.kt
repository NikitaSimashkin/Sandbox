package ru.kram.provider.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.kram.common.util.result.Result
import ru.kram.provider.domain.UserRepository
import ru.kram.provider_contract.UserProviderApi
import ru.kram.provider_contract.model.user.User

class UserRepositoryImpl(
	private val userProviderApi: UserProviderApi,
	dispatcher: CoroutineDispatcher
): UserRepository {

	private val _users = MutableStateFlow(emptyList<User>())

	private val scope = CoroutineScope(dispatcher)

	init {
		scope.launch {
			updateUsers()
		}
	}

	override fun addUser(user: User) {
		scope.launch {
			userProviderApi.insertUser(user)
			updateUsers()
		}
	}

	override suspend fun getUser(id: Long): User? {
		val result = scope.async {
			userProviderApi.getUser(id)
		}
		val user = result.await()
		Log.d(TAG, "getUser: $user")
		return when (user) {
			is Result.Success -> user.value
			is Result.Error -> null
		}
	}

	override fun getAllUsers(): StateFlow<List<User>> {
		return _users
	}

	override fun deleteUser(id: Long) {
		scope.launch {
			userProviderApi.deleteUser(id)
			updateUsers()
		}
	}

	override fun updateUser(user: User) {
		scope.launch {
			userProviderApi.updateUser(user)
			updateUsers()
		}
	}

	private fun updateUsers() {
		scope.launch {
			when (val result = userProviderApi.getUsers()) {
				is Result.Success -> _users.value = result.value
				is Result.Error -> Log.e(TAG, "updateUsers: ${result.error}")
			}
		}
	}

	companion object {
		private const val TAG = "UserRepositoryImpl"
	}
}