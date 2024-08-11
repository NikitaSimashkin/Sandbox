package ru.kram.sandbox.biglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.kram.sandbox.R
import ru.kram.sandbox.biglist.presentation.color.ColorListViewHolder
import ru.kram.sandbox.biglist.presentation.color.ColorViewHolder
import ru.kram.sandbox.biglist.presentation.model.UserListItem
import ru.kram.sandbox.biglist.presentation.user.UserViewHolder

class UserListAdapter(
	private val onClick: (UserListItem) -> Unit
): ListAdapter<UserListItem, ViewHolder>(UserDiffUtil()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return when(viewType) {
			USER_VIEW_TYPE -> {
				val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
				UserViewHolder(view, onClick)
			}

			LIST_VIEW_TYPE -> {
				val view = LayoutInflater.from(parent.context).inflate(R.layout.list_color_item, parent, false)
				ColorListViewHolder(view)
			}

			else -> throw IllegalArgumentException("Unknown viewType: $viewType")
		}
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		when(holder) {
			is UserViewHolder -> holder.bind(getItem(position) as UserListItem.UserUi)
			is ColorListViewHolder -> holder.bind(getItem(position) as UserListItem.ColorList)
		}
	}

	override fun getItemId(position: Int): Long {
		return when (val item = getItem(position)) {
			is UserListItem.UserUi -> item.id
			is UserListItem.ColorList -> item.list.hashCode().toLong()
		}
	}

	override fun getItemViewType(position: Int): Int {
		return when (getItem(position)) {
			is UserListItem.UserUi -> USER_VIEW_TYPE
			is UserListItem.ColorList -> LIST_VIEW_TYPE
		}
	}

	override fun onViewRecycled(holder: ViewHolder) {
		super.onViewRecycled(holder)
		if (holder is ColorListViewHolder) {

		}
	}

	companion object {
		private const val USER_VIEW_TYPE = 0
		private const val LIST_VIEW_TYPE = 1
	}
}