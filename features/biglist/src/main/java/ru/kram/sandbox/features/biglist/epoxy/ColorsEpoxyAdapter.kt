package ru.kram.sandbox.features.biglist.epoxy

import com.airbnb.epoxy.EpoxyAdapter
import ru.kram.sandbox.features.biglist.presentation.model.UserListItem

internal class ColorsEpoxyAdapter: EpoxyAdapter() {

    fun submitList(list: List<UserListItem.Color>) {
        addModels(
            list.map {
                EpoxyColorViewModel_().id(it.value).color(it)
            }
        )
    }
}