package ru.kram.sandbox.common.contract_user_provider

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import ru.kram.sandbox.common.utils.KResult
import ru.kram.sandbox.common.contract_user_provider.model.user.User
import ru.kram.sandbox.common.contract_user_provider.UserProviderApi.Companion.BASE_URI
import ru.kram.sandbox.common.contract_user_provider.model.user.EuropeFootSize
import ru.kram.sandbox.common.contract_user_provider.model.user.SantimetersHumanHeight
import ru.kram.sandbox.common.contract_user_provider.model.user.KilogramsHumanWeight
import ru.kram.sandbox.common.contract_user_provider.model.user.mapper.UserToContentValueMapper
import java.lang.Exception
import java.lang.ref.WeakReference

interface UserProviderApi {
	suspend fun getUsers(): KResult<List<User>, Exception>
	suspend fun getUser(id: Long): KResult<User, Exception>
	suspend fun getUsersCount(): KResult<Int, Exception>
	suspend fun insertUser(user: User): KResult<Unit, Exception>
	suspend fun deleteUser(id: Long): KResult<Unit, Exception>
	suspend fun updateUser(user: User): KResult<Unit, Exception>
	suspend fun clearUsers(): KResult<Unit, Exception>

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
		const val AUTHORITY = "ru.kram.userprovider.UserContentProvider"
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
	override suspend fun getUsers(): KResult<List<User>, Exception> {
		contentResolver.query(BASE_URI, null, null, null, null).use { cursor ->
			if (cursor == null) {
				return KResult.Error(Exception("No data"))
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
				return KResult.Success(users)
			}
		}
	}

	override suspend fun insertUser(user: User): KResult<Unit, Exception> {
		val uri = contentResolver.insert(BASE_URI, userToContentValueMapper(user))
		return if (uri == null) {
			Log.e("UserProviderApi", "Insert error")
			KResult.Error(Exception("Insert error"))
		} else {
			Log.d("UserProviderApi", "Insert success")
			KResult.Success(Unit)
		}
	}

	override suspend fun deleteUser(id: Long): KResult<Unit, Exception> {
		val selection = "${UserProviderApi.Projection.ID} = ?"
		val selectionArgs = arrayOf(id.toString())
		val count = contentResolver.delete(BASE_URI, selection, selectionArgs)
		return if (count == 0) {
			KResult.Error(Exception("Delete error"))
		} else {
			KResult.Success(Unit)
		}
	}

	override suspend fun updateUser(user: User): KResult<Unit, Exception> {
		val selection = "${UserProviderApi.Projection.ID} = ?"
		val selectionArgs = arrayOf(user.id.toString())
		val count = contentResolver.update(BASE_URI, userToContentValueMapper(user), selection, selectionArgs)
		return if (count == 0) {
			KResult.Error(Exception("Update error"))
		} else {
			KResult.Success(Unit)
		}
	}

	override suspend fun clearUsers(): KResult<Unit, Exception> {
		val count = contentResolver.delete(BASE_URI, null, null)
		return if (count == 0) {
			KResult.Error(Exception("Clear error"))
		} else {
			KResult.Success(Unit)
		}
	}

	@SuppressLint("Range")
	override suspend fun getUser(id: Long): KResult<User, Exception> {
		val selection = "${UserProviderApi.Projection.ID} = ?"
		val selectionArgs = arrayOf(id.toString())
		contentResolver.query(BASE_URI, null, selection, selectionArgs, null).use { cursor ->
			if (cursor == null) {
				return KResult.Error(Exception("No data"))
			} else {
				cursor.moveToFirst()
				val name = cursor.getString(cursor.getColumnIndex(UserProviderApi.Projection.NAME))
				val surname = cursor.getString(cursor.getColumnIndex(UserProviderApi.Projection.SURNAME))
				val age = cursor.getInt(cursor.getColumnIndex(UserProviderApi.Projection.AGE))
				val height = cursor.getInt(cursor.getColumnIndex(UserProviderApi.Projection.HEIGHT))
				val weight = cursor.getInt(cursor.getColumnIndex(UserProviderApi.Projection.WEIGHT))
				val footSize = cursor.getInt(cursor.getColumnIndex(UserProviderApi.Projection.FOOT_SIZE))
				val user = User(id, name, surname, age, SantimetersHumanHeight(height), KilogramsHumanWeight(weight), EuropeFootSize(footSize))
				return KResult.Success(user)
			}
		}
	}

	override suspend fun getUsersCount(): KResult<Int, Exception> {
		contentResolver.query(BASE_URI, null, null, null, null).use { cursor ->
			if (cursor == null) {
				return KResult.Error(Exception("No data"))
			} else {
				return KResult.Success(cursor.count)
			}
		}
	}
}