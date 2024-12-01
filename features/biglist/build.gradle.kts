plugins {
    id("ru.kram.sandbox.library")
    kotlin("kapt")
}

android {
    namespace = "ru.kram.sandbox.features.biglist"

    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation(project(":common:core"))
    implementation(project(":common:utils"))
    implementation(project(":common:koin"))

    implementation(libs.epoxy)
    kapt(libs.epoxy.processor)

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)
    implementation(libs.google.material)
    implementation(libs.glide)
    implementation(libs.viewbinding.delegate)
}
