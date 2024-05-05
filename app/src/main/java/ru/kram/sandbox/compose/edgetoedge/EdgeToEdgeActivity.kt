package ru.kram.sandbox.compose.edgetoedge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.kram.sandbox.utils.backgroundButtonColor

class EdgeToEdgeActivity: ComponentActivity() {

    private val vm by viewModels<EdgeToEdgeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Box(modifier = Modifier
                .fillMaxSize()
            ) {
                Content()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        vm.command
            .flowWithLifecycle(this.lifecycle)
            .onEach {
                when (it) {
                    EdgeToEdgeViewModel.Command.EnableEdgeToEdge -> {
                        enableEdgeToEdge()
                    }
                    EdgeToEdgeViewModel.Command.DisableEdgeToEdge -> {
                        recreate()
                    }
                }
            }
            .launchIn(lifecycle.coroutineScope)
    }

    @Composable
    private fun Content() {
        val currentState by vm.state.collectAsStateWithLifecycle()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (currentState.isConsumedStatusBarPadding)
                        Modifier.consumeWindowInsets(WindowInsets.statusBars)
                    else
                        Modifier
                )
                .then(
                    if (currentState.isSystemBarsPaddingEnabled)
                        Modifier.systemBarsPadding()
                    else
                        Modifier
                )
                .then(
                    if (currentState.isStatusBarPaddingEnabled)
                        Modifier.statusBarsPadding()
                    else
                        Modifier
                )
                .then(
                    if (currentState.isNavigationBarPaddingEnabled)
                        Modifier.navigationBarsPadding()
                    else
                        Modifier
                )
                .then(
                    if (currentState.isSafeDrawingPaddingEnabled)
                        Modifier.safeDrawingPadding()
                    else
                        Modifier
                )
                .then(
                    if (currentState.isDisplayCutoutPaddingEnabled)
                        Modifier.displayCutoutPadding()
                    else
                        Modifier
                )
                .then(
                    if (currentState.isImePaddingEnabled)
                        Modifier.imePadding()
                    else
                        Modifier
                )
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                when (currentState.screen) {
                    EdgeToEdgeScreen.NavigationScreen -> Navigation(currentState)
                }
            }

            if (currentState.screen != EdgeToEdgeScreen.NavigationScreen) {
                Button(
                    onClick = {
                        vm.navigateToNavigationScreen()
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
    fun Navigation(state: EdgeToEdgeState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.LightGray),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TopButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                state = state
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.height(800.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    Button(
                        onClick = { vm.enableEdgeToEdge() }
                    ) {
                        Text("Enable edge to edge")
                    }
                }

                item {
                    Button(
                        onClick = { vm.disableEdgeToEdge() }
                    ) {
                        Text("Disable edge to edge")
                    }
                }

                item {
                    Button(
                        onClick = { vm.enableStatusBarPadding() }
                    ) {
                        Text("Enable status bar")
                    }
                }

                item {
                    Button(
                        onClick = { vm.disableStatusBarPadding() }
                    ) {
                        Text("Disable status bar")
                    }
                }

                item {
                    Button(
                        onClick = { vm.enableNavigationBarPadding() }
                    ) {
                        Text("Enable nav bar")
                    }
                }

                item {
                    Button(
                        onClick = { vm.disableNavigationBarPadding() }
                    ) {
                        Text("Disable nav bar")
                    }
                }

                item {
                    Button(
                        onClick = { vm.enableSafeDrawingPadding() }
                    ) {
                        Text("Enable safe drawing")
                    }
                }

                item {
                    Button(
                        onClick = { vm.disableSafeDrawingPadding() }
                    ) {
                        Text("Disable safe drawing")
                    }
                }

                item {
                    Button(
                        onClick = { vm.enableSystemBarsPadding() }
                    ) {
                        Text("Enable system bars")
                    }
                }

                item {
                    Button(
                        onClick = { vm.disableSystemBarsPadding() }
                    ) {
                        Text("Disable system bars")
                    }
                }

                item {
                    Button(
                        onClick = { vm.enableDisplayCutoutPadding() }
                    ) {
                        Text("Enable display cutout")
                    }
                }

                item {
                    Button(
                        onClick = { vm.disableDisplayCutoutPadding() }
                    ) {
                        Text("Disable display cutout")
                    }
                }

                item {
                    Button(
                        onClick = { vm.enableImePadding() }
                    ) {
                        Text("Enable ime")
                    }
                }

                item {
                    Button(
                        onClick = { vm.disableImePadding() }
                    ) {
                        Text("Disable ime")
                    }
                }

                item(
                    span = {
                        GridItemSpan(2)
                    }
                ) {
                    var value by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = value ,
                        onValueChange = {
                            value = it
                        }
                    )
                }

                item {
                    Button(
                        onClick = { vm.consumeStatusBarPadding() }
                    ) {
                        Text("Consume status bar")
                    }
                }

                item {
                    Button(
                        onClick = { vm.unconsumeStatusBarPadding() }
                    ) {
                        Text("Unconsume status bar")
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                BottomButton(
                    modifier = Modifier,
                    state = state
                )
            }
        }
    }

    @Composable
    private fun TopButton(
        state: EdgeToEdgeState,
        modifier: Modifier = Modifier
    ) {
        Button(
            onClick = { vm.changeStatusBarPaddingOnButton() },
            modifier = modifier
                .then(
                    if (state.isStatusBarPaddingOnButtonEnabled)
                        Modifier
                            .statusBarsPadding()
                            .statusBarsPadding()
                    else
                        Modifier
                )
                .width(100.dp)
                .height(50.dp),
            colors = backgroundButtonColor(
                if (state.isStatusBarPaddingOnButtonEnabled) Color.Green else Color.Red
            )
        ) {
            Text(
                if (state.isStatusBarPaddingOnButtonEnabled) "Disable" else "Enable"
            )
        }
    }

    @Composable
    private fun BottomButton(
        state: EdgeToEdgeState,
        modifier: Modifier = Modifier
    ) {
        Button(
            modifier = modifier
                .then(
                    if (state.isNavigationBarPaddingOnButtonEnabled)
                        Modifier.navigationBarsPadding()
                    else
                        Modifier
                )
                .width(100.dp)
                .height(50.dp),
            onClick = {
                vm.changeNavigationBarPaddingOnButton()
            },
            colors = backgroundButtonColor(
                if (state.isNavigationBarPaddingOnButtonEnabled) Color.Green else Color.Red
            )
        ) {
            Text(
                if (state.isNavigationBarPaddingOnButtonEnabled) "Disable" else "Enable"
            )
        }
    }
}