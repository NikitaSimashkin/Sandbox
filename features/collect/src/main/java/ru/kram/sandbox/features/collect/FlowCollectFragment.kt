package ru.kram.sandbox.features.collect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

class FlowCollectFragment: Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return ComposeView(requireContext()).apply {
			setContent {
				FlowCollectScreen()
			}
		}
	}

	@Composable
	@Preview
	private fun FlowCollectScreen() {
		val viewModel: FlowCollectViewModel = viewModel()
		val items by viewModel.numbers.collectAsStateWithLifecycle()
		LazyColumn {
			items(items.size) { index ->
				Text(text = items[index].toString())
			}
		}
	}
}