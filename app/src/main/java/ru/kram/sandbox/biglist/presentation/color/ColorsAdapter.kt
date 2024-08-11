package ru.kram.sandbox.biglist.presentation.color

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.kram.sandbox.R
import ru.kram.sandbox.biglist.presentation.model.UserListItem

class ColorsAdapter: ListAdapter<UserListItem.Color, ColorViewHolder>(ColorDiffUtil()) {

	private var rightPadding: Int? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
		val view = LayoutInflater
			.from(parent.context)
			.inflate(R.layout.color_item, parent, false)
		return ColorViewHolder(view)
	}

	override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
		holder.bind(color = getItem(position))
		if (position == itemCount - 1) {
			holder.itemView.setPadding(0, 0, rightPadding(holder.itemView.context), 0)
		}
	}

	private fun rightPadding(context: Context): Int {
		return if (rightPadding == null) {
			context.resources.getDimensionPixelSize(R.dimen.color_padding).apply {
				rightPadding = this
			}
		} else {
			requireNotNull(rightPadding)
		}
	}
}