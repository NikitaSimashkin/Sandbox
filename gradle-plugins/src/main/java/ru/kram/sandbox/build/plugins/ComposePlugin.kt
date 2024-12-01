package ru.kram.sandbox.build.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import ru.kram.sandbox.build.utils.composePlugin
import ru.kram.sandbox.build.utils.libs

class ComposePlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply(composePlugin)

        target.extensions.configure<ComposeCompilerGradlePluginExtension> {
            reportsDestination.set(target.layout.buildDirectory.dir("compose_compiler"))
        }

        val extension = target.extensions.findByType(LibraryExtension::class.java)
            ?: target.extensions.findByType(ApplicationExtension::class.java)

        extension?.buildFeatures?.compose = true
    }
}