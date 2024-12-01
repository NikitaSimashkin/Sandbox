package ru.kram.sandbox.build.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

val Project.libs: VersionCatalog
    get()  = this.extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

fun Project.setupCompose() {
    dependencies.apply {
        val compose = project(":common:compose")
        add("implementation", compose)
    }

    plugins.apply(composePlugin)
}

val appPlugin = "com.android.application"
val libraryPlugin = "com.android.library"
val kotlinPlugin = "org.jetbrains.kotlin.android"

val composePlugin = "org.jetbrains.kotlin.plugin.compose"

val cacheFixPlugin = "org.gradle.android.cache-fix"
val vkomposePlugin = "com.vk.vkompose"
