package ru.kram.sandbox.features.biglist.domain

import kotlinx.coroutines.flow.Flow
import ru.kram.sandbox.features.biglist.domain.model.User

internal interface UserRepository {
	fun startLoadUsers()

	fun stopLoadUsers()
	fun getUsersFlow(): Flow<List<User>>
}