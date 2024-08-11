package ru.kram.sandbox.paging3.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.Url
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRemoteDataSource(
    private val httpClient: HttpClient,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getPokemon(offset: Int, limit: Int): PokemonDto = withContext(dispatcher) {
        return@withContext httpClient.get(
            url = Url("https://pokeapi.co/api/v2/pokemon?offset=$offset&limit=$limit")
        ).body()
    }
}