package ru.kram.provider.presentation.userlist.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.kram.provider.databinding.UserlistItemBinding
import ru.kram.provider.presentation.model.UserUi

class UserViewHolder(
	view: View,
	private val onUserClickListener: UserAdapter.OnUserClickListener,
	private val onRemoveButtonClickListener: UserAdapter.OnRemoveButtonClickListener
) : RecyclerView.ViewHolder(view) {
	private val binding = UserlistItemBinding.bind(view)

	fun bind(user: UserUi) {
		with(binding) {
			id.text = user.id.toString()
			name.text = user.name
			surname.text = user.surname
			age.text = user.age.toString()
			height.text = user.height.toString()
			weight.text = user.weight.toString()
			footsize.text = user.footSize.toString()
		}

		binding.root.setOnClickListener {
			onUserClickListener.onUserClick(user)
		}
		binding.removeButton.setOnClickListener {
			onRemoveButtonClickListener.onRemoveButtonClick(user)
		}
	}
}