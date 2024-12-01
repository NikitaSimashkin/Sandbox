package ru.kram.userprovider.presentation.model

data class UserUi(
	val id: Long,
	val name: String,
	val surname: String,
	val age: Int,
	val height: Int,
	val weight: Int,
	val footSize: Int
)