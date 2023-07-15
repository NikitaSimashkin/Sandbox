package ru.kram.sandbox.recyclerfocus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

import ru.kram.sandbox.R

class PersonAdapter : ListAdapter<Person, PersonViewHolder>(PersonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = currentList[position]
        holder.bind(person)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}
