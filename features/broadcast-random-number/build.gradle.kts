plugins {
    id("ru.kram.sandbox.library")
}

android {
    namespace = "ru.kram.sandbox.features.broadcast_random_number"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":common:contract-broadcast-random-name"))
    implementation(project(":common:contract-deathstar"))

    implementation(libs.viewbinding.delegate)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.core)
    implementation(libs.androidx.constraintlayout)
}
