import ru.kram.sandbox.build.utils.setupCompose

plugins {
    id("ru.kram.sandbox.library")
}

android {
    namespace = "ru.kram.sandbox.features.tvcompose"

    buildFeatures {
        compose = true
    }
}

setupCompose(isTv = true)

dependencies {
    implementation(libs.coil)
    implementation(libs.koin)
    implementation(libs.bundles.exoplayer)
    implementation(libs.koin.compose)
}
