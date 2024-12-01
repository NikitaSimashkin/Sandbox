package ru.kram.sandbox.features.biglist.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import ru.kram.sandbox.common.koin.injectUnsafe
import ru.kram.sandbox.features.biglist.R
import ru.kram.sandbox.features.biglist.databinding.FragmentBigListEpoxyBinding
import ru.kram.sandbox.features.biglist.epoxy.UserItemListController
import ru.kram.sandbox.features.biglist.presentation.stateholder.UserViewModel

class BigListEpoxyFragment: Fragment(R.layout.fragment_big_list_epoxy) {

    private val binding by viewBinding(FragmentBigListEpoxyBinding::bind)

    private val viewModel: UserViewModel by injectUnsafe()
    private var controller: UserItemListController? = null
    private var scrollState: Bundle = Bundle()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val controller = UserItemListController {
            viewModel.openRandomFragmentOnTop(requireActivity())
        }.apply {
            controller = this
            if (!scrollState.isEmpty) {
                onRestoreInstanceState(scrollState)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.startLoadUsers()
            viewModel.getItems().collect { users ->
                controller.setData(users)
            }
        }

        with(binding) {
            rvBigList.setDelayMsWhenRemovingAdapterOnDetach(1)
            rvBigList.setController(controller)
            rvBigList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onDestroyView() {
        viewModel.stopLoadUsers()
        controller?.onSaveInstanceState(scrollState)
        controller = null
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("EpoxyBigList", "onSaveInstanceState")
    }
}