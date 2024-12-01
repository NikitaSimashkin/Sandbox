package ru.kram.userprovider.presentation.mapper

import ru.kram.userprovider.presentation.model.UserUi
import ru.kram.sandbox.common.contract_user_provider.model.user.EuropeFootSize
import ru.kram.sandbox.common.contract_user_provider.model.user.SantimetersHumanHeight
import ru.kram.sandbox.common.contract_user_provider.model.user.KilogramsHumanWeight
import ru.kram.sandbox.common.contract_user_provider.model.user.User

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