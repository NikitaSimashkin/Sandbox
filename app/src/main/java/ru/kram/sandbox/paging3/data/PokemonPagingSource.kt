package ru.kram.sandbox.paging3.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.CancellationException
import ru.kram.sandbox.paging3.data.db.PokemonEntity
import ru.kram.sandbox.paging3.data.db.PokemonPagingKey
import timber.log.Timber

class PokemonPagingSource(
    private val pokemonRepository: PokemonRepository,
    private val basePageSize: Int
) : PagingSource<PokemonPagingKey, PokemonEntity>() {

    override suspend fun load(
        params: LoadParams<PokemonPagingKey>
    ): LoadResult<PokemonPagingKey, PokemonEntity> {
        Timber.d("load: params=${params::class.simpleName}, pageSize=${params.loadSize}")
        val page = params.key ?: PokemonPagingKey(0)
        val loadResult = try {
            val response = pokemonRepository.getFromDb(
                offset = page.page * basePageSize,
                limit = params.loadSize
            )
            Timber.d("load: page=${page.page} responseSize=${response.size}")
            LoadResult.Page(
                data = response,
                prevKey = getPrevKey(response.firstOrNull()),
                nextKey = getNextKey(response.lastOrNull())
            )
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            Timber.e(e, "load: error")
            LoadResult.Error(e)
        }
        return loadResult
    }

    override fun getRefreshKey(
        state: PagingState<PokemonPagingKey, PokemonEntity>
    ): PokemonPagingKey? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        val page = (anchorPage.data.firstOrNull()?.id ?: 0) / basePageSize
        Timber.d("getRefreshKey: anchorPosition=$anchorPosition, pokemonPagingKey=$page")
        return PokemonPagingKey(page)
    }

    private fun getNextKey(lastLoadedItem: PokemonEntity?): PokemonPagingKey? {
        if (lastLoadedItem == null) return null
        val itemPage = (lastLoadedItem.id - 1) / basePageSize
        return PokemonPagingKey(itemPage + 1)
    }

    private fun getPrevKey(firstLoadedItem: PokemonEntity?): PokemonPagingKey? {
        if (firstLoadedItem == null) return null
        val itemPage = (firstLoadedItem.id - 1) / basePageSize
        return if (itemPage == 0) null else PokemonPagingKey(itemPage - 1)
    }

    class Factory(
        private val pokemonRepository: PokemonRepository,
    ) : (Int) -> PokemonPagingSource {

        override fun invoke(pageSize: Int): PokemonPagingSource {
            return PokemonPagingSource(pokemonRepository, pageSize)
        }
    }
}
