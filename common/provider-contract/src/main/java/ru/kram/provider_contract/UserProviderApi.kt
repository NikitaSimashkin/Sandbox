package ru.kram.provider_contract

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import ru.kram.provider_contract.model.user.User
import ru.kram.common.util.result.Result
import ru.kram.provider_contract.UserProviderApi.Companion.BASE_URI
import ru.kram.provider_contract.model.user.EuropeFootSize
import ru.kram.provider_contract.model.user.SantimetersHumanHeight
import ru.kram.provider_contract.model.user.KilogramsHumanWeight
import ru.kram.provider_contract.model.user.mapper.UserToContentValueMapper
import java.lang.Exception
import java.lang.ref.WeakReference

interface UserProviderApi {
	suspend fun getUsers(): Result<List<User>, Exception>
	suspend fun getUser(id: Long): Result<User, Exception>
	suspend fun getUsersCount(): Result<Int, Exception>
	suspend fun insertUser(user: User): Result<Unit, Exception>
	suspend fun deleteUser(id: Long): Result<Unit, Exception>
	suspend fun updateUser(user: User): Result<Unit, Exception>
	suspend fun clearUsers(): Result<Unit, Exception>

	object Projection {
		const val ID = "id"
		const val NAME = "name"
		const val SURNAME = "surname"
		const val AGE = "age"
		const val HEIGHT = "height"
		const val WEIGHT = "weight"
		const val FOOT_SIZE = "foot_size"
	}

	companion object {
		const val AUTHORITY = "ru.kram.provider.UserContentProvider"
		const val TABLE_NAME = "users"
		val BASE_URI = Uri.parse("content://$AUTHORITY/$TABLE_NAME")

		fun create(contentResolver: ContentResolver): UserProviderApi = UserProviderApiImpl(contentResolver, UserToContentValueMapper())
	}
}

class UserProviderApiImpl internal constructor(
	contentResolver: ContentResolver,
	private val userToContentValueMapper: UserToContentValueMapper
): UserProviderApi {

	private val contentResolverRef = WeakReference(contentResolver)
	private val contentResolver get() = contentResolverRef.get()!!

	@SuppressLint("Range")
	override suspend fun getUsers(): Result<List<User>, Exception> {
		contentResolver.query(BASE_URI, null, null, null, null).use { cursor ->
			if (cursor == null) {
				return Result.Error(Exception("No data"))
			} else {
				val users = mutableListOf<User>()
				while (cursor.moveToNext()) {
					val id = cursor.getLong(cursor.getColumnIndex(UserProviderApi.Projection.ID))
					val name = cursor.getString(cursor.getColumnIndex(UserProviderApi.Projection.NAME))
					val surname = cursor.getString(cursor.getColumnIndex(UserProviderApi.Projection.SURNAME))
					val age = cursor.getInt(cursor.getColumnIndex(UserProviderApi.Projection.AGE))
					val height = cursor.getInt(cursor.getColumnIndex(UserProviderApi.Projection.HEIGHT))
					val weight = cursor.getInt(cursor.getColumnIndex(UserProviderApi.Projection.WEIGHT))
					val footSize = cursor.getInt(cursor.getColumnIndex(UserProviderApi.Projection.FOOT_SIZE))
					val user = User(id, name, surname, age, SantimetersHumanHeight(height), KilogramsHumanWeight(weight), EuropeFootSize(footSize))
					users.add(user)
				}
				return Result.Success(users)
			}
		}
	}

	override suspend fun insertUser(user: User): Result<Unit, Exception> {
		val uri = contentResolver.insert(BASE_URI, userToContentValueMapper(user))
		return if (uri == null) {
			Log.e("UserProviderApi", "Insert error")
			Result.Error(Exception("Insert error"))
		} else {
			Log.d("UserProviderApi", "Insert success")
			Result.Success(Unit)
		}
	}

	override suspend fun deleteUser(id: Long): Result<Unit, Exception> {
		val selection = "${UserProviderApi.Projection.ID} = ?"
		val selectionArgs = arrayOf(id.toString())
		val count = contentResolver.delete(BASE_URI, selection, selectionArgs)
		return if (count == 0) {
			Result.Error(Exception("Delete error"))
		} else {
			Result.Success(Unit)
		}
	}

	override suspend fun updateUser(user: User): Result<Unit, Exception> {
		val selection = "${UserProviderApi.Projection.ID} = ?"
		val selectionArgs = arrayOf(user.id.toString())
		val count = contentResolver.update(BASE_URI, userToContentValueMapper(user), selection, selectionArgs)
		return if (count == 0) {
			Result.Error(Exception("Update error"))
		} else {
			Result.Success(Unit)
		}
	}

	override suspend fun clearUsers(): Result<Unit, Exception> {
		val count = contentResolver.delete(BASE_URI, null, null)
		return if (count == 0) {
			Result.Error(Exception("Clear error"))
		} else {
			Result.Success(Unit)
		}
	}

	@SuppressLint("Range")
	override suspend fun getUser(id: Long): Result<User, Exception> {
		val selection = "${UserProviderApi.Projection.ID} = ?"
		val selectionArgs = arrayOf(id.toString())
		contentResolver.query(BASE_URI, null, selection, selectionArgs, null).use { cursor ->
			if (cursor == null) {
				return Result.Error(Exception("No data"))
			} else {
				cursor.moveToFirst()
				val name = cursor.getString(cursor.getColumnIndex(UserProviderApi.Projection.NAME))
				val surname = cursor.getString(cursor.getColumnIndex(UserProviderApi.Projection.SURNAME))
				val age = cursor.getInt(cursor.getColumnIndex(UserProviderApi.Projection.AGE))
				val height = cursor.getInt(cursor.getColumnIndex(UserProviderApi.Projection.HEIGHT))
				val weight = cursor.getInt(cursor.getColumnIndex(UserProviderApi.Projection.WEIGHT))
				val footSize = cursor.getInt(cursor.getColumnIndex(UserProviderApi.Projection.FOOT_SIZE))
				val user = User(id, name, surname, age, SantimetersHumanHeight(height), KilogramsHumanWeight(weight), EuropeFootSize(footSize))
				return Result.Success(user)
			}
		}
	}

	override suspend fun getUsersCount(): Result<Int, Exception> {
		contentResolver.query(BASE_URI, null, null, null, null).use { cursor ->
			if (cursor == null) {
				return Result.Error(Exception("No data"))
			} else {
				return Result.Success(cursor.count)
			}
		}
	}
}