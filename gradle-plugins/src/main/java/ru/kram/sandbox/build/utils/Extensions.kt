package ru.kram.sandbox.build.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

val Project.libs: VersionCatalog
    get()  = this.extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

fun Project.setupCompose(isTv: Boolean = false) {
    plugins.apply(composePlugin)

    dependencies.apply {
        val composeBom = platform(libs.findLibrary("androidx_compose_bom").get())
        val composeBase = libs.findBundle("compose_base").get()
        val composeUi = if (isTv) libs.findBundle("compose_tv").get() else libs.findBundle("compose_mobile").get()
        val coil = libs.findLibrary("coil").get()
        val composeUiTools = libs.findLibrary("androidx_compose_ui_tools").get()

        add("implementation", composeBom)
        add("implementation", composeBase)
        add("implementation", composeUi)
        add("implementation", coil)

        add("debugImplementation", composeUiTools)
    }
}

const val appPlugin = "com.android.application"
const val libraryPlugin = "com.android.library"
const val kotlinPlugin = "org.jetbrains.kotlin.android"

const val composePlugin = "org.jetbrains.kotlin.plugin.compose"

const val cacheFixPlugin = "org.gradle.android.cache-fix"
const val vkomposePlugin = "com.vk.vkompose"
