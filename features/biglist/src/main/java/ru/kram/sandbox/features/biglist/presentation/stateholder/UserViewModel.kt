package ru.kram.sandbox.features.biglist.presentation.stateholder

import android.graphics.Color
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import ru.kram.sandbox.features.biglist.domain.FragmentOpener
import ru.kram.sandbox.features.biglist.domain.UserRepository
import ru.kram.sandbox.features.biglist.presentation.mapper.UserToUserUiMapper
import ru.kram.sandbox.features.biglist.presentation.model.UserListItem
import kotlin.random.Random

internal class UserViewModel(
	private val repository: UserRepository,
	private val mapper: UserToUserUiMapper,
	private val fragmentOpener: FragmentOpener,
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

	fun openRandomFragmentOnTop(activity: FragmentActivity) {
		fragmentOpener.openRandomFragmentOnTop(activity)
	}

	private fun generateListColor(count: Int): UserListItem.ColorList {
		val colors = mutableListOf<UserListItem.Color>()
		for (i in 0 until count) {
			val color = Color.valueOf(Random.nextInt(256).toFloat(), Random.nextInt(256).toFloat(), Random.nextInt(256).toFloat())
			colors.add(UserListItem.Color(color.toArgb()))
		}
		return UserListItem.ColorList(colors)
	}
}