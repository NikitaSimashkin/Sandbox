package ru.kram.sandbox.head.dependencies

import androidx.fragment.app.FragmentActivity
import ru.kram.sandbox.features.biglist.domain.FragmentOpener
import ru.kram.sandbox.features.compose.ComposeNavigationFragment
import ru.kram.sandbox.head.R

class FragmentOpenerImpl: FragmentOpener {

    override fun openRandomFragmentOnTop(activity: FragmentActivity) {
        activity.supportFragmentManager.beginTransaction().apply {
            addToBackStack(null)
            replace(R.id.main_container_fragment, ComposeNavigationFragment())
            commit()
        }
    }
}