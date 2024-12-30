package ru.kram.sandbox.build.plugins

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import ru.kram.sandbox.build.Config
import ru.kram.sandbox.build.utils.appPlugin
import ru.kram.sandbox.build.utils.cacheFixPlugin
import ru.kram.sandbox.build.utils.kotlinPlugin

open class BaseAppPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply(appPlugin)
        target.pluginManager.apply(kotlinPlugin)
        target.pluginManager.apply(cacheFixPlugin)

        target.extensions.configure<ApplicationExtension> {
            compileSdk = Config.compileSdkVersion

            defaultConfig {
                minSdk = Config.minSdkVersion
                targetSdk = Config.targetSdkVersion

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

            target.extensions.configure<ApplicationExtension> {
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

            testOptions {
                unitTests {
                    isIncludeAndroidResources = true
                }
            }

            buildTypes {
                getByName("debug") {
                    isMinifyEnabled = false
                    applicationIdSuffix = ".debug"
                    versionNameSuffix = "-debug"
                }
                getByName("release") {
                    isMinifyEnabled = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                    versionNameSuffix = "-release"
                }
            }
        }
    }
}