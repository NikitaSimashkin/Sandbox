package ru.kram.pokemonpaging.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel
import ru.kram.sandbox.common.compose.ComposeFragment
import ru.kram.pokemonpaging.presentation.composable.Pages
import ru.kram.pokemonpaging.presentation.composable.PokemonItem
import ru.kram.pokemonpaging.presentation.model.PokemonUiModel

class PokemonFragment: ComposeFragment() {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val viewModel: PokemonViewModel = koinViewModel()
            val pokemonState = viewModel.pokemonState.collectAsStateWithLifecycle()
            val pokemonList = viewModel.pokemonsFlow.collectAsLazyPagingItems()
            val scrollState = rememberLazyListState()

            Title()

            ActionButtons(viewModel)

            HorizontalDivider()

            InfoBlock(pokemonState.value.pokemonsDbCount, pokemonList)

            HorizontalDivider()

            PokemonList(
                pokemonList = pokemonList,
                scrollState = scrollState,
                onDelete = {
                    viewModel.deletePokemon(it)
                },
                modifier = Modifier.weight(1f)
            )

            val columns = 12
            Pages(
                rows = (pokemonState.value.pageInfo.pages.size - 1) / columns + 1,
                columns = columns,
                pagesInfo = pokemonState.value.pageInfo,
                onClick = viewModel::onPageClick
            )

            HandleCommand(
                viewModel = viewModel,
                pagingList = pokemonList,
                lazyListState = scrollState
            )
        }
    }

    @Composable
    private fun PokemonList(
        onDelete: (PokemonUiModel) -> Unit,
        scrollState: LazyListState,
        pokemonList: LazyPagingItems<PokemonUiModel>,
        modifier: Modifier = Modifier
    ) {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            state = scrollState
        ) {
            items(
                count = pokemonList.itemCount,
                key = pokemonList.itemKey { it.id }
            ) {
                val pokemon = pokemonList[it]
                PokemonItem(
                    pokemon = pokemon,
                    onDelete = {
                        if (pokemon != null) {
                            onDelete(pokemon)
                        }
                    }
                )
                if (pokemonList.itemCount - 1 == it) {
                    HorizontalDivider()
                }
            }
        }
    }

    @Composable
    private fun InfoBlock(
        pokemonsDbCount: Int,
        pokemons: LazyPagingItems<PokemonUiModel>
    ) {
        Text(
            text = "Pokemons in DB: $pokemonsDbCount",
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            text = "Pokemons in memory: ${pokemons.itemSnapshotList.count { it != null }}",
            style = MaterialTheme.typography.bodySmall
        )
    }

    @Composable
    private fun Title() {
        Text(
            text = "Pokemon List",
            style = MaterialTheme.typography.headlineSmall
        )
    }

    @Composable
    @OptIn(ExperimentalLayoutApi::class)
    private fun ActionButtons(viewModel: PokemonViewModel) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ActionButton(text = "Invalidate pager", onClick = viewModel::invalidate)
            ActionButton(text = "Clear Database", onClick = viewModel::clearDatabase)
            ActionButton(text = "Refresh list", onClick = viewModel::onRefresh)
            ActionButton(text = "Retry list", onClick = viewModel::onRetry)
            ActionButton(text = "Load fake data", onClick = viewModel::loadFakeData)
        }
    }

    @Composable
    private fun ActionButton(text: String, onClick: () -> Unit) {
        Button(
            onClick = onClick
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

    @Composable
    private fun HandleCommand(
        viewModel: PokemonViewModel,
        pagingList: LazyPagingItems<PokemonUiModel>,
        lazyListState: LazyListState
    ) {
        LaunchedEffect(Unit) {
            viewModel.commands.onEach { command ->
                when (command) {
                    is PokemonViewModel.PokemonCommand.ScrollToPosition -> {
                        lazyListState.animateScrollToItem(index = command.position)
                    }
                    is PokemonViewModel.PokemonCommand.RetryList -> {
                        pagingList.retry()
                    }
                    is PokemonViewModel.PokemonCommand.RefreshList -> {
                        pagingList.refresh()
                    }
                    is PokemonViewModel.PokemonCommand.ShowError -> {
                        Toast.makeText(requireContext(), command.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.launchIn(this)
        }
    }
}