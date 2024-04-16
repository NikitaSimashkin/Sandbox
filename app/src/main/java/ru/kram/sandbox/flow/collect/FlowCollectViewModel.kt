package ru.kram.sandbox.flow.collect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FlowCollectViewModel: ViewModel() {

	private val numbersInternal = MutableStateFlow(emptyList<Int>())
	val numbers: StateFlow<List<Int>> = numbersInternal

	init {
		viewModelScope.launch {
			numbersInternal.value = getRandomNumbersList()
			delay(2000)
		}
	}

	private fun getRandomNumbersList(): List<Int> {
		return (0..10).map { (0..100).random() }
	}
}