plugins {
    id("ru.kram.sandbox.library")
}

android {
    namespace = "ru.kram.sandbox.features.recyclerfocus"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)
    implementation(libs.google.material)

    implementation(libs.viewbinding.delegate)
    implementation(libs.media3)
}
