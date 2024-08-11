package ru.kram.sandbox.biglist.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import ru.kram.sandbox.R
import ru.kram.sandbox.biglist.epoxy.UserItemListController
import ru.kram.sandbox.biglist.presentation.stateholder.UserViewModel
import ru.kram.sandbox.biglist.presentation.stateholder.UserViewModelFactory
import ru.kram.sandbox.compose.ComposeNavigationFragment
import ru.kram.sandbox.databinding.FragmentBigListEpoxyBinding

class BigListEpoxyFragment: Fragment(R.layout.fragment_big_list_epoxy) {

    private val binding by viewBinding(FragmentBigListEpoxyBinding::bind)

    private val viewModel: UserViewModel by viewModels { UserViewModelFactory() }
    private var controller: UserItemListController? = null
    private var scrollState: Bundle = Bundle()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val controller = UserItemListController {
            parentFragmentManager.beginTransaction().apply {
                addToBackStack(null)
                replace(R.id.main_container_fragment, ComposeNavigationFragment())
                commit()
            }
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