package ru.kram.sandbox.recyclerfocus

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kram.sandbox.R

class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
	private val nameTextView: TextView = itemView.findViewById(R.id.text_name)
	private val surnameTextView: TextView = itemView.findViewById(R.id.text_surname)

	fun bind(person: Person) {
		nameTextView.text = person.name
		surnameTextView.text = person.surname
	}
}