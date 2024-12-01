package ru.kram.sandbox.android

import android.content.Intent
import android.os.Handler
import android.os.Looper

class IntentTest {

    fun main() {
        Intent().apply {
            data
            extras
        }

        Handler(Looper.getMainLooper())
    }
}