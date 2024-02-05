package ru.kram.provider_contract.model.user

data class User(
	val id: Long,
	val name: String,
	val surname: String,
	val age: Int,
	val height: SantimetersHumanHeight,
	val weight: KilogramsHumanWeight,
	val footSize: EuropeFootSize,
)