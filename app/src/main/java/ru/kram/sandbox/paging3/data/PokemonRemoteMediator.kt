package ru.kram.sandbox.paging3.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import ru.kram.sandbox.paging3.data.db.PokemonEntity
import ru.kram.sandbox.paging3.data.db.PokemonPagingKey
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val pokemonRepository: PokemonRepository
): RemoteMediator<PokemonPagingKey, PokemonEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<PokemonPagingKey, PokemonEntity>
    ): MediatorResult {
        Timber.d("load: loadType=$loadType")
        val page = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> {
                Timber.d("load PREPEND, endOfPaginationReached=true")
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val lastLoadedId = pokemonRepository.getLastLoadedPokemonId()
                if (lastLoadedId == null) {
                    0
                } else {
                    ((lastLoadedId - 1) / state.config.pageSize) + 1
                }
            }
        }
        val mediatorResult = try {
            val response = pokemonRepository.getFromNetwork(
                offset = state.config.pageSize * page,
                limit = state.config.pageSize
            )
            Timber.d("load $loadType: responseSize=${response.size}, page=$page, lastLoadedId = ${pokemonRepository.getLastLoadedPokemonId()}")
            Timber.d("load $response")
            pokemonRepository.insertAll(response)

            MediatorResult.Success(endOfPaginationReached = response.isEmpty())
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            Timber.e(e, "load: error")
            MediatorResult.Error(e)
        }
        return mediatorResult
    }
}