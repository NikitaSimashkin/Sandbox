package ru.kram.sandbox.paging3.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import ru.kram.sandbox.paging3.data.db.PokemonDataBase
import ru.kram.sandbox.paging3.data.db.PokemonEntity
import ru.kram.sandbox.paging3.data.network.PokemonRemoteDataSource
import ru.kram.sandbox.paging3.domain.model.Pokemon

class PokemonRepository(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource,
    private val pokemonDataBase: PokemonDataBase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val pokemonDao = pokemonDataBase.pokemonDao()


    suspend fun getFromNetwork(offset: Int, limit: Int): List<Pokemon> = withContext(dispatcher) {
        return@withContext pokemonRemoteDataSource.getPokemon(offset, limit).results.map {
            val id = it.url.split("/").let { parts -> parts[parts.size - 2].toInt() }
            Pokemon(
                id = id,
                name = it.name,
                imageUrl = getImageUrl(id)
            )
        }
    }

    suspend fun getFromDb(offset: Int, limit: Int): List<PokemonEntity> = withContext(dispatcher) {
        return@withContext pokemonDao.get(offset = offset, limit = limit)
    }

    suspend fun insertAll(pokemons: List<Pokemon>) = withContext(dispatcher) {
        pokemonDao.insertAll(
            pokemons.map {
                PokemonEntity(
                    id = it.id,
                    name = it.name,
                    imageUrl = it.imageUrl
                )
            }
        )
    }

    suspend fun clearAll() = withContext(dispatcher) {
        pokemonDao.clearAll()
    }

    suspend fun getLastLoadedPokemonId(): Int? = withContext(dispatcher) {
        return@withContext pokemonDao.getLastLoadedPokemonId()
    }

    fun observeCount(): Flow<Int> {
        return pokemonDao.observeCount().flowOn(dispatcher)
    }

    private fun getImageUrl(id: Int): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    }
}