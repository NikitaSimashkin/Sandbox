package ru.kram.userprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.util.Log
import org.koin.android.ext.android.inject
import ru.kram.userprovider.data.database.UserDatabase
import ru.kram.userprovider.data.database.mapper.UserContentValuesToUserDbMapper
import ru.kram.sandbox.common.contract_user_provider.UserProviderApi
import timber.log.Timber
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

class UserContentProvider : ContentProvider() {

	private val userDatabase by inject<UserDatabase>()
	private val userContentValuesToUserDbMapper by inject<UserContentValuesToUserDbMapper>()

	private val userDao get() = userDatabase.userDao()

	private val USER_TABLE_CODE = 1
	private val USER_ITEM_CODE = 2
	private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
		addURI(UserProviderApi.AUTHORITY, UserProviderApi.TABLE_NAME, USER_TABLE_CODE)
		addURI(UserProviderApi.AUTHORITY, "${UserProviderApi.TABLE_NAME}/#", USER_ITEM_CODE)
	}

	override fun onCreate(): Boolean {
		Timber.d("onCreate")
		return true
	}

	override fun insert(uri: Uri, values: ContentValues?): Uri {
		assertValuesNotNull(values)

		Timber.d("insert, uri=$uri, values=$values")

		userDatabase.openHelper.run {
			when (uriMatcher.match(uri)) {
				USER_TABLE_CODE -> {
					val id = userDao.insertUser(userContentValuesToUserDbMapper(values))
					val resultUri = Uri.withAppendedPath(uri, id.toString())
					notify(resultUri)
					return resultUri
				}

				else -> unknownUri(uri)
			}
		}
	}

	override fun query(
		uri: Uri,
		projection: Array<out String>?,
		selection: String?,
		selectionArgs: Array<out String>?,
		sortOrder: String?
	): Cursor {
		Log.d(
			TAG,
			"query, uri=$uri, projection=$projection, selection=$selection, selectionArgs=$selectionArgs, sortOrder=$sortOrder"
		)

		return userDatabase.openHelper.run {
			when (uriMatcher.match(uri)) {
				USER_TABLE_CODE -> {
					var query = if (projection == null) {
						"SELECT * FROM ${UserProviderApi.TABLE_NAME}"
					} else {
						"SELECT ${projection.joinToString()} FROM ${UserProviderApi.TABLE_NAME}"
					}
					if (selection != null) {
						query += " WHERE $selection"
					}
					if (sortOrder != null) {
						query += " ORDER BY $sortOrder"
					}
					Timber.d(query)
					readableDatabase.query(query, selectionArgs ?: emptyArray())
				}

				USER_ITEM_CODE -> {
					val id = uri.lastPathSegment?.toLongOrNull()
					if (id != null) {
						val query =
							"SELECT * FROM ${UserProviderApi.TABLE_NAME} WHERE ${UserProviderApi.Projection.ID} = $id"
						Timber.d(query)
						readableDatabase.query(query, selectionArgs ?: emptyArray())
					} else {
						unknownUri(uri)
					}
				}

				else -> unknownUri(uri)
			}
		}
	}

	override fun getType(uri: Uri): String? {
		return null
	}

	override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
		Timber.d("delete, uri=$uri, selection=$selection, selectionArgs=$selectionArgs")
		return userDatabase.openHelper.run {
			when (uriMatcher.match(uri)) {
				USER_TABLE_CODE -> {
					val rowAffected = writableDatabase.delete(
						UserProviderApi.TABLE_NAME,
						selection,
						selectionArgs
					)
					notifyIfNeeded(rowAffected, uri)
					rowAffected
				}

				USER_ITEM_CODE -> {
					val id = uri.lastPathSegment?.toLongOrNull()
					if (id != null) {
						val itemSelection = "${UserProviderApi.Projection.ID} = ?"
						val itemSelectionArgs = arrayOf(id.toString())
						val rowAffected = writableDatabase.delete(
							UserProviderApi.TABLE_NAME,
							itemSelection,
							itemSelectionArgs
						)
						notifyIfNeeded(rowAffected, uri)
						rowAffected
					} else {
						unknownUri(uri)
					}
				}

				else -> unknownUri(uri)
			}
		}
	}

	override fun update(
		uri: Uri,
		values: ContentValues?,
		selection: String?,
		selectionArgs: Array<out String>?
	): Int {
		assertValuesNotNull(values)

		Log.d(
			TAG,
			"update, uri=$uri, values=$values, selection=$selection, selectionArgs=$selectionArgs"
		)
		return userDatabase.openHelper.run {
			when (uriMatcher.match(uri)) {
				USER_TABLE_CODE -> {
					val rowAffected =
						writableDatabase.update(
							UserProviderApi.TABLE_NAME,
							SQLiteDatabase.CONFLICT_REPLACE,
							values,
							selection,
							selectionArgs
						)
					notifyIfNeeded(rowAffected, uri)
					rowAffected
				}

				USER_ITEM_CODE -> {
					val id = uri.lastPathSegment?.toLongOrNull()
					if (id != null) {
						val itemSelection = "${UserProviderApi.Projection.ID} = ?"
						val itemSelectionArgs = arrayOf(id.toString())
						val rowAffected = writableDatabase.update(
							UserProviderApi.TABLE_NAME,
							SQLiteDatabase.CONFLICT_REPLACE,
							values,
							itemSelection,
							itemSelectionArgs
						)
						notifyIfNeeded(rowAffected, uri)
						rowAffected
					} else {
						unknownUri(uri)
					}
				}

				else -> unknownUri(uri)
			}
		}
	}

	override fun shutdown() {
		Timber.d("shutdown")
		super.shutdown()
	}

	private fun unknownUri(uri: Uri): Nothing {
		throw IllegalArgumentException("Unknown URI: $uri")
	}

	private fun notifyIfNeeded(rowAffected: Int, uri: Uri) {
		if (rowAffected > 0) {
			notify(uri)
		}
	}

	private fun notify(uri: Uri) {
		context?.contentResolver?.notifyChange(uri, null)
	}

	@OptIn(ExperimentalContracts::class)
	private fun <T> assertValuesNotNull(values: T?) {
		contract {
			returns() implies (values != null)
		}
		if (values == null) {
			throw IllegalArgumentException("Values must not be null")
		}
	}

	companion object {
		private const val TAG = "UserContentProvider"
	}
}