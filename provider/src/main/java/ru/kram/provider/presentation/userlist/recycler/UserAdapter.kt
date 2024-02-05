package ru.kram.provider.presentation.userlist.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kram.provider.R
import ru.kram.provider.presentation.model.UserUi

class UserAdapter(
	private val onUserClickListener: OnUserClickListener,
	private val onRemoveButtonClickListener: OnRemoveButtonClickListener
): ListAdapter<UserUi, UserViewHolder>(UserDiffUtil()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.userlist_item, parent, false)
		return UserViewHolder(view, onUserClickListener, onRemoveButtonClickListener)
	}

	override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
		holder.bind(getItem(position))
	}

	override fun getItemCount(): Int = currentList.size

	fun setUsers(users: List<UserUi>) {
		submitList(users)
	}

	fun interface OnUserClickListener {
		fun onUserClick(user: UserUi)
	}

	fun interface OnRemoveButtonClickListener {
		fun onRemoveButtonClick(user: UserUi)
	}
}