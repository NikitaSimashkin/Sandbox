plugins {
    id("ru.kram.sandbox.library")
}

android {
    namespace = "ru.kram.sandbox.features.contentprovider"

    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation(project(":common:core"))
    implementation(project(":common:utils"))

    implementation(project(":common:contract-user-provider"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)

    implementation(libs.google.material)
    implementation(libs.viewbinding.delegate)
}
