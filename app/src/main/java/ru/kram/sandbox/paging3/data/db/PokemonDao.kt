package ru.kram.sandbox.paging3.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM PokemonEntity ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun get(offset: Int, limit: Int): List<PokemonEntity>

    @Query("SELECT COUNT(*) FROM PokemonEntity")
    fun observeCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemons: List<PokemonEntity>)

    @Update
    fun update(pokemons: List<PokemonEntity>)

    @Query("DELETE FROM PokemonEntity")
    fun clearAll()

    @Query("SELECT MAX(id) FROM PokemonEntity")
    fun getLastLoadedPokemonId(): Int?
}