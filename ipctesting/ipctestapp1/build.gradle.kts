plugins {
	id("ru.kram.sandbox.application")
}

android {
	namespace = "ru.kram.ipctestapp1"

	defaultConfig {
		applicationId = "ru.kram.ipctestapp1"
		versionCode = 1
		versionName = "1.0"
	}

	buildFeatures {
		viewBinding = true
	}
}

dependencies {
	implementation(project(":common:utils"))
	implementation(project(":common:contract-random-messenger"))
	implementation(project(":common:contract-broadcast-random-name"))

	implementation(libs.androidx.core)
	implementation(libs.androidx.appcompat)
	implementation(libs.androidx.constraintlayout)
	implementation(libs.google.material)

	implementation(libs.androidx.activity)
	implementation(libs.androidx.fragment)

	implementation(libs.viewbinding.delegate)
}