package ru.kram.sandbox.carousel

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kram.sandbox.R

class CarouselHolder(view: View): RecyclerView.ViewHolder(view) {

	fun bind(text: String) {
		itemView.findViewById<TextView>(R.id.carousel_text).text = text
	}
}