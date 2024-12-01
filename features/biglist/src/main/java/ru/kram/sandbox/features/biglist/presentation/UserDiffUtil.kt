package ru.kram.sandbox.features.biglist.presentation

import androidx.recyclerview.widget.DiffUtil
import ru.kram.sandbox.features.biglist.presentation.model.UserListItem

internal class UserDiffUtil: DiffUtil.ItemCallback<UserListItem>() {

	override fun areContentsTheSame(
		oldItem: UserListItem,
		newItem: UserListItem
	): Boolean {
		return oldItem == newItem
	}

	override fun areItemsTheSame(
		oldItem: UserListItem,
		newItem: UserListItem): Boolean {
		return when {
			oldItem is UserListItem.UserUi && newItem is UserListItem.UserUi -> {
				oldItem.id == newItem.id
			}
			oldItem is UserListItem.ColorList && newItem is UserListItem.ColorList -> {
				oldItem == newItem
			}
			else -> false
		}
	}
}