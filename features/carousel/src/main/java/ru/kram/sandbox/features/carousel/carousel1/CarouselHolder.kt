package ru.kram.sandbox.features.carousel.carousel1

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kram.sandbox.features.carousel.R

internal class CarouselHolder(view: View): RecyclerView.ViewHolder(view) {

	val textView = itemView.findViewById<TextView>(R.id.carousel_text)

	fun bind(text: String) {
		textView.text = text
	}
}