plugins {
    id("ru.kram.sandbox.library")
}

android {
    namespace = "ru.kram.sandbox.features.service"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":common:utils"))
    implementation(project(":common:contract-deathstar"))
    implementation(project(":common:contract-random-messenger"))
    implementation(project(":common:koin"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)

    implementation(libs.google.material)
    implementation(libs.viewbinding.delegate)

}
