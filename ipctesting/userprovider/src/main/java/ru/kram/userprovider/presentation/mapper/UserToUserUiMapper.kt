package ru.kram.userprovider.presentation.mapper

import ru.kram.userprovider.presentation.model.UserUi
import ru.kram.sandbox.common.contract_user_provider.model.user.User

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