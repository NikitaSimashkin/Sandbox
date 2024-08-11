package ru.kram.sandbox.biglist.presentation.color

import androidx.recyclerview.widget.DiffUtil
import ru.kram.sandbox.biglist.presentation.model.UserListItem

class ColorDiffUtil: DiffUtil.ItemCallback<UserListItem.Color>() {

        override fun areContentsTheSame(
            oldItem: UserListItem.Color,
            newItem: UserListItem.Color
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: UserListItem.Color,
            newItem: UserListItem.Color): Boolean {
            return oldItem == newItem
        }
}