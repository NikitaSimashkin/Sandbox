package ru.kram.sandbox.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log

class NumberContentProvider: ContentProvider() {

	override fun onCreate(): Boolean {
		Log.d(TAG, "onCreate")
		return false
	}

	override fun insert(uri: Uri, values: ContentValues?): Uri {
		Log.d(TAG, "insert, uri=$uri, values=$values")
		return uri
	}

	override fun query(
		uri: Uri,
		projection: Array<out String>?,
		selection: String?,
		selectionArgs: Array<out String>?,
		sortOrder: String?
	): Cursor? {
		Log.d(TAG, "query, uri=$uri, projection=$projection, selection=$selection, selectionArgs=$selectionArgs, sortOrder=$sortOrder")
		return null
	}

	override fun getType(uri: Uri): String {
		Log.d(TAG, "getType, uri=$uri")
		return ""
	}

	override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
		Log.d(TAG, "delete, uri=$uri, selection=$selection, selectionArgs=$selectionArgs")
		return 5
	}

	override fun update(
		uri: Uri,
		values: ContentValues?,
		selection: String?,
		selectionArgs: Array<out String>?
	): Int {
		Log.d(TAG, "update, uri=$uri, values=$values, selection=$selection, selectionArgs=$selectionArgs")
		return 5
	}

	override fun shutdown() {
		Log.d(TAG, "shutdown")
		super.shutdown()
	}

	companion object {
		private const val TAG = "NumberContentProvider"
	}
}