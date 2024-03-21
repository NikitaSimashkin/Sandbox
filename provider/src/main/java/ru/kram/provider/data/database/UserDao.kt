package ru.kram.provider.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.kram.provider.data.database.entities.UserEntity
import ru.kram.provider_contract.UserProviderApi

@Dao
interface UserDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertUser(user: UserEntity)

	@Update
	fun updateUser(user: UserEntity)

	@Query("SELECT * FROM ${UserProviderApi.TABLE_NAME} WHERE ${UserProviderApi.Projection.ID} = :id")
	fun getUserById(id: Long): UserEntity

	@Query("SELECT * FROM ${UserProviderApi.TABLE_NAME}")
	fun getAllUsers(): List<UserEntity>

	@Delete(entity = UserEntity::class)
	fun deleteUser(user: UserEntity)

	@Query("DELETE FROM ${UserProviderApi.TABLE_NAME} WHERE ${UserProviderApi.Projection.ID} = :id")
	fun deleteUserById(id: Long)

	@Query("SELECT * FROM ${UserProviderApi.TABLE_NAME}")
	fun getAllUsersFlow(): Flow<List<UserEntity>>
}