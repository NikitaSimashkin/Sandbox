package ru.kram.sandbox.paging3.data.db.fake

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonFakeEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String
)