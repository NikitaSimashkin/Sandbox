plugins {
	id("ru.kram.sandbox.application")
	id("com.google.devtools.ksp")
}

android {
	namespace = "ru.kram.userprovider"

	defaultConfig {
		applicationId = "ru.kram.userprovider"
		versionCode = 1
		versionName = "1.0"

		ksp {
			arg("room.schemaLocation", "$projectDir/schemas")
		}
	}

	buildFeatures {
		viewBinding = true
	}
}

dependencies {
	implementation(project(":common:core"))
	implementation(project(":common:utils"))
	implementation(project(":common:contract-user-provider"))
	implementation(project(":common:koin"))

	implementation(libs.androidx.core)
	implementation(libs.androidx.activity)
	implementation(libs.androidx.fragment)
	implementation(libs.google.material)

	implementation(libs.viewbinding.delegate)
	implementation(libs.bundles.room)
	ksp(libs.room.compiler)
}