package ru.kram.sandbox.paging3.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        PokemonEntity::class
    ],
    version = 1
)
abstract class PokemonDataBase: RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}