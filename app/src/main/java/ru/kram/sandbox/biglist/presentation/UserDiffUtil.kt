package ru.kram.sandbox.biglist.presentation

import androidx.recyclerview.widget.DiffUtil
import ru.kram.sandbox.biglist.presentation.model.UserUi

class UserDiffUtil: DiffUtil.ItemCallback<UserUi>() {

	override fun areContentsTheSame(oldItem: UserUi, newItem: UserUi): Boolean {
		return oldItem == newItem
	}

	override fun areItemsTheSame(oldItem: UserUi, newItem: UserUi): Boolean {
		return oldItem.id == newItem.id
	}
}