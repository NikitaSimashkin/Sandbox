package ru.kram.provider.domain

import kotlinx.coroutines.flow.StateFlow
import ru.kram.provider_contract.model.user.User

interface UserRepository {
	fun addUser(user: User)
	suspend fun getUser(id: Long): User?
	fun getAllUsers(): StateFlow<List<User>>
	fun deleteUser(id: Long)
	fun updateUser(user: User)
}