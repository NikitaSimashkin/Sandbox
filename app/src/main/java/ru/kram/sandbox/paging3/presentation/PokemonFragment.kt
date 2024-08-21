package ru.kram.sandbox.paging3.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import org.koin.androidx.compose.koinViewModel
import ru.kram.sandbox.paging3.presentation.composable.PokemonItem
import ru.kram.sandbox.paging3.presentation.model.PokemonUiModel
import ru.kram.sandbox.utils.ComposeFragment

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

            Title()

            ActionButtons(viewModel, pokemonList)

            HorizontalDivider()

            InfoBlock(pokemonState.value.pokemonsDbCount, pokemonList)

            HorizontalDivider()

            PokemonList(
                pokemonList = pokemonList,
                onDelete = {
                    viewModel.deletePokemon(it)
                }
            )
        }
    }

    @Composable
    private fun PokemonList(
        onDelete: (PokemonUiModel) -> Unit,
        pokemonList: LazyPagingItems<PokemonUiModel>
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
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
            text = "Pokemons in memory: ${pokemons.itemSnapshotList.size}",
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
    private fun ActionButtons(
        viewModel: PokemonViewModel,
        pokemonList: LazyPagingItems<PokemonUiModel>
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ActionButton(text = "Invalidate pager", onClick = viewModel::invalidate)
            ActionButton(text = "Clear Database", onClick = viewModel::clearDatabase)
            ActionButton(text = "Refresh list", onClick = pokemonList::refresh)
            ActionButton(text = "Retry list", onClick = pokemonList::retry)
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
}