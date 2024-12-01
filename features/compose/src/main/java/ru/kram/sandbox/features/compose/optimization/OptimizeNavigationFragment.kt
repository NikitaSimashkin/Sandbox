package ru.kram.sandbox.features.compose.optimization

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.kram.sandbox.common.compose.ComposeFragment
import ru.kram.sandbox.features.compose.optimization.nonrestartable.NonRestartableScreen

class OptimizeNavigationFragment: ComposeFragment() {

    @Composable
    override fun Content() {
        val vm: OptimizeViewModel = viewModel()
        val state by vm.state.collectAsStateWithLifecycle()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                when (state.screen) {
                    OptimizeScreen.NavigationScreen -> Navigation(vm::navigateTo)
                    OptimizeScreen.NonRestartableScreen -> NonRestartableScreen()
                }
            }

            if (state.screen != OptimizeScreen.NavigationScreen) {
                Button(
                    onClick = {
                        vm.navigateTo(OptimizeScreen.NavigationScreen)
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("Go Back")
                }
            }
        }
    }

    @Composable
    private fun Navigation(
        onScreenClick: (OptimizeScreen) -> Unit
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            listOf(
                OptimizeScreen.NonRestartableScreen to "NonRestartable"
            ).forEach { (composeScreen, name) ->
                item {
                    Button(
                        onClick = { onScreenClick(composeScreen) }
                    ) {
                        Text(name)
                    }
                }
            }
        }
    }
}