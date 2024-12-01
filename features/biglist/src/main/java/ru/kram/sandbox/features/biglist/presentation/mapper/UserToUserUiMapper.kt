package ru.kram.sandbox.features.biglist.presentation.mapper

import ru.kram.sandbox.features.biglist.domain.model.User
import ru.kram.sandbox.features.biglist.presentation.model.UserListItem

internal class UserToUserUiMapper: (User) -> UserListItem.UserUi {

	override fun invoke(user: User): UserListItem.UserUi {
		return UserListItem.UserUi(
			id = user.id,
			name = user.name,
			surname = user.surname,
			age = user.age,
			avatarUrl = user.avatarUrl
		)
	}
}