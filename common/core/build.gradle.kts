plugins {
    id("ru.kram.sandbox.library")
}

android {
    namespace = "ru.kram.sandbox.common.core"
}

dependencies {
    api(libs.timber)
    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.coroutines.android)
}
