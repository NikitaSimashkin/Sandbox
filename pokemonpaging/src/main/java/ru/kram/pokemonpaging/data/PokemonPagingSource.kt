package ru.kram.pokemonpaging.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.CancellationException
import ru.kram.pokemonpaging.data.db.PokemonEntity
import ru.kram.pokemonpaging.data.db.PokemonPagingKey
import timber.log.Timber

class PokemonPagingSource(
    private val pokemonRepository: PokemonRepository,
    private val basePageSize: Int,
) : PagingSource<PokemonPagingKey, PokemonEntity>() {

    override val keyReuseSupported: Boolean = true

    override suspend fun load(
        params: LoadParams<PokemonPagingKey>
    ): LoadResult<PokemonPagingKey, PokemonEntity> {
        Timber.d("load: params=${params::class.simpleName}, pageSize=${params.loadSize}")
        val page = params.key ?: PokemonPagingKey(0)
        return try {
            val response = pokemonRepository.getFromDb(
                offset = page.page * basePageSize,
                limit = params.loadSize
            )
            Timber.d("load: page=${page.page} responseSize=${response.size}")

            val firstItem = response.firstOrNull()
            val lastItem = response.lastOrNull()
            val itemsBefore = firstItem?.id?.minus(1) ?: LoadResult.Page.COUNT_UNDEFINED
            val itemsAfter = lastItem?.id?.let { pokemonRepository.getCountAfter(it) }
                ?: LoadResult.Page.COUNT_UNDEFINED
            LoadResult.Page(
                data = response,
                prevKey = getPrevKey(firstItem),
                nextKey = getNextKey(lastItem),
                itemsBefore = itemsBefore,
                itemsAfter = itemsAfter
            )
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            Timber.e(e, "load: error")
            LoadResult.Error(e)
        }
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
            return PokemonPagingSource(
                pokemonRepository = pokemonRepository,
                basePageSize = pageSize
            )
        }
    }
}
