package ru.kram.sandbox.paging3.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.kram.sandbox.paging3.data.PokemonPagingSource
import ru.kram.sandbox.paging3.data.PokemonRemoteMediator
import ru.kram.sandbox.paging3.data.PokemonRepository
import ru.kram.sandbox.paging3.presentation.model.PokemonScreenState
import ru.kram.sandbox.paging3.presentation.model.PokemonUiModel

@OptIn(ExperimentalPagingApi::class)
class PokemonViewModel(
    pokemonRemoteMediator: PokemonRemoteMediator,
    private val pokemonRepository: PokemonRepository,
    private val pagingSourceFactory: PokemonPagingSource.Factory
) : ViewModel() {

    private var pokemonPagingSource: PokemonPagingSource? = null

    val pokemonState = MutableStateFlow(PokemonScreenState(pokemonsDbCount = 0))

    val pokemonsFlow = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = PAGE_SIZE * 2,
            prefetchDistance = PAGE_SIZE / 4,
            maxSize = PAGE_SIZE * 3
        ),
        pagingSourceFactory = {
            pagingSourceFactory(PAGE_SIZE).apply {
                pokemonPagingSource = this
            }
        },
        remoteMediator = pokemonRemoteMediator
    ).flow
        .map {
            it.map { pokemon ->
                PokemonUiModel(
                    id = pokemon.id,
                    name = pokemon.name,
                    imageUrl = pokemon.imageUrl
                )
            }
        }
        .cachedIn(viewModelScope)

    init {
        pokemonRepository.observeCount().distinctUntilChanged().onEach { count ->
            pokemonPagingSource?.invalidate()
            pokemonState.value = PokemonScreenState(count)
        }.launchIn(viewModelScope)
    }

    fun invalidate() {
        pokemonPagingSource?.invalidate()
    }

    fun clearDatabase() {
        viewModelScope.launch {
            pokemonRepository.clearAll()
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}