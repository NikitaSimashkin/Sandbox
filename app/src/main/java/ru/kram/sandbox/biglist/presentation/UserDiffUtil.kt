package ru.kram.sandbox.biglist.presentation

import androidx.recyclerview.widget.DiffUtil
import ru.kram.sandbox.biglist.presentation.model.UserListItem

class UserDiffUtil: DiffUtil.ItemCallback<UserListItem>() {

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