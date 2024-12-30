package ru.kram.pokemonpaging.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.kram.pokemonpaging.data.PokemonPagingSource
import ru.kram.pokemonpaging.data.PokemonRemoteMediator
import ru.kram.pokemonpaging.data.PokemonRepository
import ru.kram.pokemonpaging.presentation.model.Page
import ru.kram.pokemonpaging.presentation.model.PagesInfo
import ru.kram.pokemonpaging.presentation.model.PokemonScreenState
import ru.kram.pokemonpaging.presentation.model.PokemonUiModel
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class PokemonViewModel(
    pokemonRemoteMediatorFactory: PokemonRemoteMediator.Factory,
    private val pokemonRepository: PokemonRepository,
    private val pagingSourceFactory: PokemonPagingSource.Factory
) : ViewModel() {

    private var pokemonPagingSource: PokemonPagingSource? = null

    val pokemonState = MutableStateFlow(PokemonScreenState(
        pokemonsDbCount = 0,
        pageInfo = PagesInfo(pages = emptyList())
    ))
    val commands = MutableSharedFlow<PokemonCommand>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val pokemonsFlow = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = true,
            initialLoadSize = PAGE_SIZE,
            prefetchDistance = PAGE_SIZE / 4,
            maxSize = PAGE_SIZE * 3,
        ),
        pagingSourceFactory = {
            pagingSourceFactory(PAGE_SIZE).apply {
                Timber.d("PagingSourceFactory: new")
                pokemonPagingSource = this
            }
        },
        remoteMediator = pokemonRemoteMediatorFactory(PAGE_SIZE)
    ).flow
        .map {
            it
                .map { pokemon ->
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
            pokemonState.update {
                val lastPage = ((count + 1) / PAGE_SIZE).coerceAtLeast(MIN_PAGES)
                it.copy(
                    pokemonsDbCount = count,
                    pageInfo = PagesInfo(
                        pages = (1..lastPage).map { page ->
                            Page(
                                value = page,
                                isEnabled = true,
                            )
                        }
                    )
                )
            }
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

    fun onRetry() {
        commands.tryEmit(PokemonCommand.RetryList)
    }

    fun onRefresh() {
        commands.tryEmit(PokemonCommand.RefreshList)
    }

    fun onPageClick(page: Int) {
        val position = (page - 1) * PAGE_SIZE
        Timber.d("onPageClick: page=$page, position=$position")
        commands.tryEmit(PokemonCommand.ScrollToPosition(position))
    }

    fun loadFakeData() {
        viewModelScope.launch {
            try {
                pokemonRepository.loadFake()
            } catch (ce: CancellationException) {
                throw ce
            } catch (ignored: Exception) {
                commands.emit(PokemonCommand.ShowError("Failed to load fake data"))
            }
        }
    }

    fun deletePokemon(pokemon: PokemonUiModel) {
        viewModelScope.launch {
            pokemonRepository.delete(pokemon.id)
        }
    }

    sealed class PokemonCommand {
        data object RefreshList : PokemonCommand()
        data object RetryList : PokemonCommand()
        data class ShowError(val message: String) : PokemonCommand()
        data class ScrollToPosition(val position: Int) : PokemonCommand()
    }

    companion object {
        private const val PAGE_SIZE = 50
        private const val MIN_PAGES = 19
    }
}