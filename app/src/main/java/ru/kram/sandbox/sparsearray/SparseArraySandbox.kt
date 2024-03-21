package ru.kram.sandbox.sparsearray

import android.util.SparseArray
import android.util.SparseBooleanArray
import android.util.SparseIntArray
import android.util.SparseLongArray
import androidx.collection.ArrayMap
import androidx.collection.SparseArrayCompat

class SparseArraySandbox {

	private val arrayMap = ArrayMap<Int, String>()
	private val arrayCustom = SparseArray<User>()
	private val arrayBoolean = SparseBooleanArray()
	private val arrayInt = SparseIntArray()
	private val arrayLong = SparseLongArray()
	private val arrayCompat = SparseArrayCompat<String>()

	private data class User(val id: Int, val name: String)

	companion object {
		private const val TAG = "SparseArraySandbox"
	}
}