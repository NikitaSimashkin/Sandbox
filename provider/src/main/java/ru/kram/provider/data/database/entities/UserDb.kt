package ru.kram.provider.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kram.provider_contract.UserProviderApi

@Entity(tableName = UserProviderApi.TABLE_NAME)
data class UserDb(
	@PrimaryKey
	@ColumnInfo(name = UserProviderApi.Projection.ID)
	val id: Long,
	@ColumnInfo(name = UserProviderApi.Projection.NAME)
	val name: String,
	@ColumnInfo(name = UserProviderApi.Projection.SURNAME)
	val surname: String,
	@ColumnInfo(name = UserProviderApi.Projection.AGE)
	val age: Int,
	@ColumnInfo(name = UserProviderApi.Projection.HEIGHT)
	val height: Int,
	@ColumnInfo(name = UserProviderApi.Projection.WEIGHT)
	val weight: Int,
	@ColumnInfo(name = UserProviderApi.Projection.FOOT_SIZE)
	val footSize: Int
)