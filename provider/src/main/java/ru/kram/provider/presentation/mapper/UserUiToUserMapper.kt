package ru.kram.provider.presentation.mapper

import ru.kram.provider.presentation.model.UserUi
import ru.kram.provider_contract.model.user.EuropeFootSize
import ru.kram.provider_contract.model.user.SantimetersHumanHeight
import ru.kram.provider_contract.model.user.KilogramsHumanWeight
import ru.kram.provider_contract.model.user.User

class UserUiToUserMapper: (UserUi) -> User {
	override fun invoke(user: UserUi): User {
		return User(
			id = user.id,
			name = user.name,
			surname = user.surname,
			age = user.age,
			height = SantimetersHumanHeight(user.height),
			weight = KilogramsHumanWeight(user.weight),
			footSize = EuropeFootSize(user.footSize)
		)
	}
}