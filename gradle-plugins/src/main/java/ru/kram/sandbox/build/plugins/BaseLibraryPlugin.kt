package ru.kram.sandbox.build.plugins

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.project
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import ru.kram.sandbox.build.Config
import ru.kram.sandbox.build.utils.cacheFixPlugin
import ru.kram.sandbox.build.utils.kotlinPlugin
import ru.kram.sandbox.build.utils.libraryPlugin

open class BaseLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply(libraryPlugin)
        target.pluginManager.apply(kotlinPlugin)
        target.pluginManager.apply(cacheFixPlugin)

        target.extensions.configure<LibraryExtension> {
            compileSdk = Config.compileSdkVersion

            defaultConfig {
                minSdk = Config.minSdkVersion
                targetSdk = Config.targetSdkVersion
                testOptions.targetSdk = Config.targetSdkVersion

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                consumerProguardFiles("consumer-rules.pro")
            }

            buildTypes {
                getByName("debug") {
                    isMinifyEnabled = false
                }
                getByName("release") {
                    isMinifyEnabled = true
                    proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                }
            }

            target.extensions.configure<LibraryExtension> {
                buildFeatures {
                    buildConfig = true
                }
            }

            compileOptions {
                sourceCompatibility = Config.java
                targetCompatibility = Config.java
            }

            (this as org.gradle.api.plugins.ExtensionAware).extensions.configure<KotlinJvmOptions> {
                jvmTarget = Config.javaString
            }

            if (target.name != "core") {
                with(target.dependencies) {
                    val core = project(":common:core")
                    add("implementation", core)
                }
            }
        }
    }
}