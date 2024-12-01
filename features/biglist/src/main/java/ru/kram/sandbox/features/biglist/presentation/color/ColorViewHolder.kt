package ru.kram.sandbox.features.biglist.presentation.color

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.kram.sandbox.features.biglist.presentation.model.UserListItem

internal class ColorViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bind(color: UserListItem.Color) {
        itemView.setBackgroundColor(color.value)
    }
}