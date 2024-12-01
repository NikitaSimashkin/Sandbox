package ru.kram.sandbox.features.biglist.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import ru.kram.sandbox.features.biglist.presentation.model.UserListItem

internal class UserItemListController(
    private val onClick: (UserListItem) -> Unit
): TypedEpoxyController<List<UserListItem>>() {

    override fun buildModels(items: List<UserListItem>) {
        items.forEach { item ->
            when (item) {
                is UserListItem.UserUi -> epoxyUserView {
                    id(item.id)
                    user(item)
                    onClick {
                        this@UserItemListController.onClick(item)
                    }
                }
                is UserListItem.ColorList -> epoxyColorListView {
                    id(item.hashCode())
                    colorList(item)
                    onClick {
                        this@UserItemListController.onClick(item)
                    }
                }
            }
        }
    }
}