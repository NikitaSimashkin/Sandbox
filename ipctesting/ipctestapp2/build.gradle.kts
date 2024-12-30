plugins {
	id("ru.kram.sandbox.application")
}

android {
	namespace = "ru.kram.ipctestapp2"

	defaultConfig {
		applicationId = "ru.kram.ipctestapp2"
		versionCode = 1
		versionName = "1.0"
	}
}

dependencies {
	implementation(project(":common:core"))
	implementation(project(":common:contract-random-messenger"))
	implementation(project(":common:utils"))

	implementation(libs.androidx.core)
}