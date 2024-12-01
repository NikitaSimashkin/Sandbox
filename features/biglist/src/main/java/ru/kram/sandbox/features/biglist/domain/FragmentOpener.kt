package ru.kram.sandbox.features.biglist.domain

import androidx.fragment.app.FragmentActivity

interface FragmentOpener {
    fun openRandomFragmentOnTop(activity: FragmentActivity)
}