import ru.kram.sandbox.build.utils.setupCompose

plugins {
    id("ru.kram.sandbox.application")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization")
}

android {
    namespace = "ru.kram.pokemonpaging"

    defaultConfig {
        applicationId = "ru.kram.pokemonpaging"
        versionCode = 1
        versionName = "1.0"
    }
}

setupCompose()

dependencies {
    implementation(project(":common:core"))
    implementation(project(":common:utils"))
    implementation(project(":common:koin"))
    implementation(project(":common:compose"))

    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    implementation(libs.bundles.ktor)

    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
    implementation(libs.paging.room)

    implementation(libs.koin.compose)
}