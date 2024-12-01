package ru.kram.sandbox.features.biglist.presentation.user

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import ru.kram.sandbox.features.biglist.databinding.UserItemBinding
import ru.kram.sandbox.features.biglist.presentation.model.UserListItem

internal class UserViewHolder(
	view: View,
	private val onClick: (UserListItem.UserUi) -> Unit
): RecyclerView.ViewHolder(view){

	private val binding by viewBinding(UserItemBinding::bind)

	@SuppressLint("SetTextI18n")
	fun bind(userUi: UserListItem.UserUi) {
		with(binding) {
			root.setOnClickListener { onClick(userUi) }
			name.text = "name: ${userUi.name}"
			surname.text =  "surname: ${userUi.surname}"
			age.text = "age: ${userUi.age}"
			Glide.with(binding.root)
				.load(userUi.avatarUrl)
				.into(avatar)
		}
	}
}

