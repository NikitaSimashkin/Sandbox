package ru.kram.sandbox.features.compose.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlin.random.Random

@Preview
@Composable
internal fun LazyGridScreen() {
    val measureGridController = remember { MeasureGridController(2) }
    val items = remember { (0 until 200).map { Random.nextInt(0, 3) } }
    val density = LocalDensity.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(items= items) { index, lineCount ->
            Box {
                var height by remember { mutableIntStateOf(MeasureGridController.NOT_MEASURED) }
                LaunchedEffect(index){
                    measureGridController.observeHeight(index)
                        .onEach { height = it }
                        .launchIn(this)
                }
                GridBlock(
                    lineCount = lineCount,
                    modifier = Modifier
                        .background(Color.LightGray)
                        .padding(8.dp)
                        .onGloballyPositioned {
                            measureGridController.onMeasured(index, it.size.height)
                        }
                        .then(
                            if (height != MeasureGridController.NOT_MEASURED) {
                                Modifier.height(with(density) { height.toDp() })
                            } else {
                                Modifier
                            }
                        )
                )
            }
        }
    }
}

@Composable
fun GridBlock(
    lineCount: Int,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column {
            (0..lineCount).forEach { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .background(
                            getColor(index = index)
                        )
                )
            }
        }
    }
}

@Composable
fun getColor(index: Int): Color {
    return when (index) {
        0 -> Color.Red
        1 -> Color.Green
        else -> Color.Blue
    }
}

class MeasureGridController(
    private val gridSize: Int
) {
    private val heightPxFlow = MutableStateFlow<Map<Int, Int>>(emptyMap())

    fun onMeasured(index: Int, heightPx: Int) {
        // Определяем строку, к которой принадлежит элемент
        val rowStartIndex = (index / gridSize) * gridSize
        val rowEndIndex = rowStartIndex + gridSize - 1

        // Определяем максимальную высоту для всех элементов строки
        val currentMaxHeight = (rowStartIndex..rowEndIndex).map { i ->
            heightPxFlow.value[i] ?: Int.MIN_VALUE
        }.maxOrNull() ?: Int.MIN_VALUE

        val newMaxHeight = maxOf(heightPx, currentMaxHeight)

        // Обновляем высоту для всех элементов в строке
        val updatedMap = heightPxFlow.value.toMutableMap()
        for (i in rowStartIndex..rowEndIndex) {
            updatedMap[i] = newMaxHeight
        }
        heightPxFlow.value = updatedMap
    }

    fun observeHeight(index: Int): Flow<Int> {
        return heightPxFlow.map {
            it[index] ?: NOT_MEASURED
        }.distinctUntilChanged()
    }

    companion object {
        const val NOT_MEASURED = -1
    }
}