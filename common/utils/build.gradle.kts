plugins {
	id("ru.kram.sandbox.library")
}

android {
	namespace = "ru.kram.sandbox.common.utils"
}

dependencies {
	implementation(libs.androidx.fragment)
}