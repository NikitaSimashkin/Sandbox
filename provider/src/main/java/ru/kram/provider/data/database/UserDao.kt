package ru.kram.provider.data.database

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.kram.provider.data.database.entities.UserDb
import ru.kram.provider_contract.UserProviderApi

@Dao
interface UserDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertUser(user: UserDb)

	@Update
	fun updateUser(user: UserDb)

	@Query("SELECT * FROM users WHERE ${UserProviderApi.Projection.ID} = :id")
	fun getUserById(id: Long): UserDb

	@Query("SELECT * FROM ${UserProviderApi.TABLE_NAME}")
	fun getAllUsers(): List<UserDb>

	@Delete(entity = UserDb::class)
	fun deleteUser(user: UserDb)

	@Query("DELETE FROM ${UserProviderApi.TABLE_NAME} WHERE ${UserProviderApi.Projection.ID} = :id")
	fun deleteUserById(id: Long)

	@Query("SELECT * FROM ${UserProviderApi.TABLE_NAME}")
	fun getAllUsersFlow(): Flow<List<UserDb>>
}