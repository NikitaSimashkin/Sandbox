plugins {
    id("ru.kram.sandbox.library")
    id("ru.kram.sandbox.compose")
}

android {
    namespace = "ru.kram.sandbox.common.compose"
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)

    api(platform(libs.androidx.compose.bom))
    api(libs.bundles.compose)
    api(libs.coil)
    
    debugApi(libs.androidx.compose.ui.tools)
}