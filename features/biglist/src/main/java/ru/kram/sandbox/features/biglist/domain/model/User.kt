package ru.kram.sandbox.features.biglist.domain.model

internal data class User(
	val id: Long,
	val name: String,
	val surname: String,
	val age: Int,
	val avatarUrl: String
)