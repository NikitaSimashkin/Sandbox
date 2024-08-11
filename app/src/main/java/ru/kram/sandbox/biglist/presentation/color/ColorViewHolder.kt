package ru.kram.sandbox.biglist.presentation.color

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.kram.sandbox.biglist.presentation.model.UserListItem

class ColorViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bind(color: UserListItem.Color) {
        itemView.setBackgroundColor(color.value)
    }
}