package ru.kram.sandbox.biglist.presentation.model


sealed interface UserListItem {
	data class UserUi(
		val id: Long,
		val name: String,
		val surname: String,
		val age: Int,
		val avatarUrl: String
	): UserListItem

	data class ColorList(
		val list: List<Color>
	): UserListItem

	data class Color(val value: Int)
}