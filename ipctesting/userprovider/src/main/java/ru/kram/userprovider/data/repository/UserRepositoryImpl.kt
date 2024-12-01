package ru.kram.userprovider.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.kram.sandbox.common.utils.KResult
import ru.kram.userprovider.domain.UserRepository
import ru.kram.sandbox.common.contract_user_provider.UserProviderApi
import ru.kram.sandbox.common.contract_user_provider.model.user.User
import timber.log.Timber

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
		Timber.d("getUser: $user")
		return when (user) {
			is KResult.Success -> user.data
			is KResult.Error -> null
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
				is KResult.Success -> _users.value = result.data
				is KResult.Error -> Log.e(TAG, "updateUsers: ${result.exception}")
			}
		}
	}

	companion object {
		private const val TAG = "UserRepositoryImpl"
	}
}