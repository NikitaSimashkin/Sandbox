package ru.kram.sandbox.biglist.presentation.mapper

import ru.kram.sandbox.biglist.domain.model.User
import ru.kram.sandbox.biglist.presentation.model.UserUi

class UserToUserUiMapper: (User) -> UserUi {

	override fun invoke(user: User): UserUi {
		return UserUi(
			id = user.id,
			name = user.name,
			surname = user.surname,
			age = user.age,
			avatarUrl = user.avatarUrl
		)
	}
}