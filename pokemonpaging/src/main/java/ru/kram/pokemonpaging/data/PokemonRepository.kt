package ru.kram.pokemonpaging.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ru.kram.pokemonpaging.data.db.PokemonDataBase
import ru.kram.pokemonpaging.data.db.PokemonEntity
import ru.kram.pokemonpaging.data.db.fake.PokemonFakeEntity
import ru.kram.pokemonpaging.data.network.PokemonRemoteDataSource
import ru.kram.pokemonpaging.model.Pokemon

class PokemonRepository(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource,
    private val pokemonDataBase: PokemonDataBase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val pokemonDao = pokemonDataBase.pokemonDao()
    private val pokemonFakeDao = pokemonDataBase.pokemonFakeDao()

    suspend fun getFromNetwork(offset: Int, limit: Int): List<Pokemon> = withContext(dispatcher) {
        delay(500)

        // используем фейковое дао вместо сети, так как с ним проще тестировать различные сценарии
        return@withContext pokemonFakeDao.get(offset = offset, limit = limit).map {
            Pokemon(
                id = it.id,
                name = it.name,
                imageUrl = it.imageUrl
            )
        }
    }

    suspend fun getFromDb(offset: Int, limit: Int): List<PokemonEntity> = withContext(dispatcher) {
        return@withContext pokemonDao.get(offset = offset, limit = limit).map {
            PokemonEntity(
                id = it.id,
                name = it.name,
                imageUrl = it.imageUrl
            )
        }
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

    suspend fun getCount(): Int = withContext(dispatcher) {
        return@withContext pokemonDao.getCount()
    }

    suspend fun getCountAfter(id: Int): Int = withContext(dispatcher) {
        return@withContext pokemonDao.getCountAfter(id)
    }

    suspend fun loadFake() = withContext(dispatcher) {
        val entities = pokemonRemoteDataSource.getPokemon(0, 100000).results.map {
            val id = it.url.split("/").let { parts -> parts[parts.size - 2].toInt() }
            PokemonFakeEntity(
                id = id,
                name = it.name,
                imageUrl = getImageUrl(id)
            )
        }
        entities.chunked(900).forEach {
            pokemonFakeDao.insertAll(it)
        }
    }

    suspend fun delete(id: Int) = withContext(dispatcher) {
        pokemonDao.delete(id)
    }

    private fun getImageUrl(id: Int): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    }
}