package ru.kram.provider.presentation.userlist.recycler

import androidx.recyclerview.widget.DiffUtil
import ru.kram.provider.presentation.model.UserUi

class UserDiffUtil: DiffUtil.ItemCallback<UserUi>() {

	override fun areItemsTheSame(oldItem: UserUi, newItem: UserUi): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: UserUi, newItem: UserUi): Boolean {
		return oldItem == newItem
	}
}