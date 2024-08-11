package ru.kram.sandbox.biglist.presentation.mapper

import ru.kram.sandbox.biglist.domain.model.User
import ru.kram.sandbox.biglist.presentation.model.UserListItem

class UserToUserUiMapper: (User) -> UserListItem.UserUi {

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