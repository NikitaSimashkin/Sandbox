plugins {
    id("ru.kram.sandbox.library")
}

android {
    namespace = "ru.kram.sandbox.features.carousel"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":common:core"))
    implementation(project(":common:utils"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.google.material)

    implementation(libs.viewbinding.delegate)
}
