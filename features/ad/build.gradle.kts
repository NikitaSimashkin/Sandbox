plugins {
    id("ru.kram.sandbox.library")
}

android {
    namespace = "ru.kram.sandbox.features.ad"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":common:core"))
    implementation(project(":common:utils"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.mobileads)
    implementation(libs.google.material)
    implementation(libs.viewbinding.delegate)
}
