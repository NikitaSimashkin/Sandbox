package ru.kram.sandbox.recyclerfocus

class PersonRepository {

	fun getPersons(): List<Person> {
		return (0..20).map { Person(it.toString(), generateRandomString()) }
	}

	private fun generateRandomString(): String {
		val size = (1..10).toList()
		val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
		val length = size.random()
		return (1..length)
			.map { allowedChars.random() }
			.joinToString("")
	}
}