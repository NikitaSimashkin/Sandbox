package ru.kram.sandbox.features.service

import android.content.Intent

interface ActivityIntentProvider {
    fun get(): Intent
}