package ru.kram.sandbox.build

import org.gradle.api.JavaVersion

object Config {
    const val minSdkVersion = 28
    const val targetSdkVersion = 35
    const val compileSdkVersion = 35
    val java = JavaVersion.VERSION_17
    val javaString = "17"
}
