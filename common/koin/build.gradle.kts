plugins {
	id("ru.kram.sandbox.library")
}

android {
	namespace = "ru.kram.sandbox.common.koin"
}

dependencies {
	api(libs.koin)
}