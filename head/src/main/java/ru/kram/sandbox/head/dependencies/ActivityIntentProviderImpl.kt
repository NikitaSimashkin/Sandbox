package ru.kram.sandbox.head.dependencies

import android.content.Context
import android.content.Intent
import ru.kram.sandbox.features.service.ActivityIntentProvider
import ru.kram.sandbox.head.MainActivity

class ActivityIntentProviderImpl(
    private val context: Context,
): ActivityIntentProvider {

    override fun get() = Intent(context, MainActivity::class.java)
}