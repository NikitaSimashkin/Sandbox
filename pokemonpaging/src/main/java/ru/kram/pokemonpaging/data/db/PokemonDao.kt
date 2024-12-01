package ru.kram.pokemonpaging.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM PokemonEntity ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun get(offset: Int, limit: Int): List<PokemonEntity>

    @Query("SELECT * FROM PokemonEntity")
    fun getPagingSource(): PagingSource<Int, PokemonEntity>

    @Query("SELECT COUNT(*) FROM PokemonEntity")
    fun observeCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM PokemonEntity")
    suspend fun getCount(): Int

    @Query("SELECT COUNT(*) FROM PokemonEntity WHERE id > :id")
    suspend fun getCountAfter(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemons: List<PokemonEntity>)

    @Update
    suspend fun update(pokemons: List<PokemonEntity>)

    @Query("DELETE FROM PokemonEntity")
    suspend fun clearAll()

    @Query("SELECT MAX(id) FROM PokemonEntity")
    suspend fun getLastLoadedPokemonId(): Int?

    @Query("DELETE FROM PokemonEntity WHERE id = :id")
    suspend fun delete(id: Int)
}