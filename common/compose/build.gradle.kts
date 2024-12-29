import ru.kram.sandbox.build.utils.setupCompose

plugins {
    id("ru.kram.sandbox.library")
}

android {
    namespace = "ru.kram.sandbox.common.compose"
}

setupCompose()

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
}