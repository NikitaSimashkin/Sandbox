package ru.kram.sandbox.biglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.kram.sandbox.R
import ru.kram.sandbox.biglist.presentation.model.UserUi

class UserAdapter: ListAdapter<UserUi, UserViewHolder>(UserDiffUtil()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
		return UserViewHolder(view)
	}

	override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
		holder.bind(getItem(position))
	}

	override fun getItemId(position: Int): Long {
		return getItem(position).id
	}
}