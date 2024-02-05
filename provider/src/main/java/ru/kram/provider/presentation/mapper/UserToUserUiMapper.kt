package ru.kram.provider.presentation.mapper

import ru.kram.provider.presentation.model.UserUi
import ru.kram.provider_contract.model.user.User

class UserToUserUiMapper: (User) -> UserUi {
	override fun invoke(user: User): UserUi {
		return UserUi(
			id = user.id,
			name = user.name,
			surname = user.surname,
			age = user.age,
			height = user.height.value,
			weight = user.weight.value,
			footSize = user.footSize.value
		)
	}
}