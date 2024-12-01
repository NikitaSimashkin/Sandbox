package ru.kram.pokemonpaging.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kram.pokemonpaging.data.db.fake.PokemonFakeDao
import ru.kram.pokemonpaging.data.db.fake.PokemonFakeEntity

@Database(
    entities = [
        PokemonEntity::class,
        PokemonFakeEntity::class
    ],
    version = 2
)
abstract class PokemonDataBase: RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonFakeDao(): PokemonFakeDao
}