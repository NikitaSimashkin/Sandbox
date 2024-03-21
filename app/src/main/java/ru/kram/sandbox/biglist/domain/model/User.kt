package ru.kram.sandbox.biglist.domain.model

data class User(
	val id: Long,
	val name: String,
	val surname: String,
	val age: Int,
	val avatarUrl: String
)