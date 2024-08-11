package ru.kram.sandbox.biglist.epoxy

import android.os.Bundle
import com.airbnb.epoxy.TypedEpoxyController
import ru.kram.sandbox.biglist.presentation.model.UserListItem

class UserItemListController(
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