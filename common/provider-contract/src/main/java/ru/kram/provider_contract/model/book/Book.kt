package ru.kram.provider_contract.model.book

data class Book(
	val id: Int,
	val title: String,
	val author: String,
	val pages: Int,
	val year: Int
)