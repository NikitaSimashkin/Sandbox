package ru.kram.sandbox.biglist.presentation.color

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.sandbox.biglist.presentation.model.UserListItem
import ru.kram.sandbox.databinding.ListColorItemBinding

class ColorListViewHolder(view: View): RecyclerView.ViewHolder(view) {

	private val binding by viewBinding(ListColorItemBinding::bind)

	fun bind(colorList: UserListItem.ColorList) {
		binding.colorList.layoutManager =
			LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)

		binding.colorList.adapter = ColorsAdapter().apply {
			submitList(colorList.list)
		}
	}
}