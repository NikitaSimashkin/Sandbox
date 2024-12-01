package ru.kram.sandbox.android

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.forEach
import java.util.Stack

fun <T: View> findViewById(root: View, id: Int): T? {
    val stack = Stack<View>()
    stack.add(root)
    while (stack.isNotEmpty()) {
        val currentView = stack.pop()

        if (currentView.id == id) {
            @Suppress("UNCHECKED_CAST")
            return currentView as T
        }

        if (currentView is ViewGroup) {
            currentView.forEach {
                stack.add(it)
            }
        }
    }

    return null
}

fun <T: View> findViewById2(root: View, id: Int): T? {
    if (root.id == id) return root as T

    if (root is ViewGroup) {
        for (view in root.children) {
            val result = findViewById2<T>(view, id)
            if (result != null) {
                return result
            }
        }
    }

    return null
}