plugins {
    id("ru.kram.sandbox.library")
}

android {
    namespace = "ru.kram.sandbox.common.test"

    packaging { resources.excludes.add("META-INF/*") }
}

dependencies {
    api(libs.junit.jupiter)
    api(libs.robolectric)
    api(libs.espresso)
}
