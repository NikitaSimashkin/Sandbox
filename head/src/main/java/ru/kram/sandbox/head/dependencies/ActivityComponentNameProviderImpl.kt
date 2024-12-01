package ru.kram.sandbox.head.dependencies

import android.content.ComponentName
import android.content.Context
import ru.kram.sandbox.features.pendingintent.ActivityComponentNameProvider
import ru.kram.sandbox.head.MainActivity

class ActivityComponentNameProviderImpl(
    private val context: Context,
): ActivityComponentNameProvider {

    override fun get() = ComponentName(context, MainActivity::class.java)
}