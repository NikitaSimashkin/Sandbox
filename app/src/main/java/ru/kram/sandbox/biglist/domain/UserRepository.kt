package ru.kram.sandbox.biglist.domain

import kotlinx.coroutines.flow.Flow
import ru.kram.sandbox.biglist.domain.model.User

interface UserRepository {
	fun startLoadUsers()

	fun stopLoadUsers()
	fun getUsersFlow(): Flow<List<User>>
}