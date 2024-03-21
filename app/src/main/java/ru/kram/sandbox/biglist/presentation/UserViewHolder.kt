package ru.kram.sandbox.biglist.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import ru.kram.sandbox.biglist.presentation.model.UserUi
import ru.kram.sandbox.databinding.UserItemBinding

class UserViewHolder(view: View): RecyclerView.ViewHolder(view) {

	private val binding by viewBinding(UserItemBinding::bind)

	fun bind(userUi: UserUi) {
		with(binding) {
			name.text = "name: ${userUi.name}"
			surname.text =  "surname: ${userUi.surname}"
			age.text = "age: ${userUi.age}"
			Glide.with(binding.root)
				.load(userUi.avatarUrl)
				.into(avatar)
		}
	}
}