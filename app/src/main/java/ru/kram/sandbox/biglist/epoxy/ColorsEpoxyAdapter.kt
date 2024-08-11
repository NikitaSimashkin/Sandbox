package ru.kram.sandbox.biglist.epoxy

import android.os.Bundle
import com.airbnb.epoxy.EpoxyAdapter
import ru.kram.sandbox.biglist.presentation.model.UserListItem

class ColorsEpoxyAdapter: EpoxyAdapter() {

    fun submitList(list: List<UserListItem.Color>) {
        addModels(
            list.map {
                EpoxyColorViewModel_().id(it.value).color(it)
            }
        )
    }
}