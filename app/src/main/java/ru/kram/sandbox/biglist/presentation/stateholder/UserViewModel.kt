package ru.kram.sandbox.biglist.presentation.stateholder

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import ru.kram.sandbox.biglist.domain.UserRepository
import ru.kram.sandbox.biglist.presentation.mapper.UserToUserUiMapper
import ru.kram.sandbox.biglist.presentation.model.UserListItem
import kotlin.random.Random

class UserViewModel(
	private val repository: UserRepository,
	private val mapper: UserToUserUiMapper
): ViewModel() {

	private val users = repository.getUsersFlow()
	private val colors = List(3) { generateListColor(10) }

	fun getItems() = combine(users, flowOf(colors)) { users, colors ->
		val items: MutableList<UserListItem> = users
			.map { mapper(it) }
			.toMutableList()

		colors.forEachIndexed { index, colorList ->
			val position = (index + 1) * 5
			if (position < items.size) {
				items.add(position, colorList)
			}
		}

		items
	}

	fun startLoadUsers() {
		repository.startLoadUsers()
	}

	fun stopLoadUsers() {
		repository.stopLoadUsers()
	}

	private fun generateListColor(count: Int): UserListItem.ColorList {
		val colors = mutableListOf<UserListItem.Color>()
		for (i in 0 until count) {
			val color = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
			colors.add(UserListItem.Color(color.toArgb()))
		}
		return UserListItem.ColorList(colors)
	}
}