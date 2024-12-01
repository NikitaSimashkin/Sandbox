package ru.kram.sandbox.features.pendingintent

import android.content.ComponentName

interface ActivityComponentNameProvider {
    fun get(): ComponentName
}