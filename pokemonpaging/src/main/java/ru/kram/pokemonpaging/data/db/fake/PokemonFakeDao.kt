package ru.kram.pokemonpaging.data.db.fake

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonFakeDao {

    @Query("SELECT * FROM PokemonFakeEntity ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun get(offset: Int, limit: Int): List<PokemonFakeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemons: List<PokemonFakeEntity>)
}