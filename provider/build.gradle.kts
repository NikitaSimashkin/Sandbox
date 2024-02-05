plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("com.google.devtools.ksp")
}

android {
	namespace = "ru.kram.provider"
	compileSdk = 34

	defaultConfig {
		applicationId = "ru.kram.provider"
		minSdk = 24
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

		ksp {
			arg("room.schemaLocation", "$projectDir/schemas")
		}

	}

	buildTypes {
		release {
			isMinifyEnabled = false
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}

	buildFeatures {
		viewBinding = true
	}

	kotlinOptions {
		jvmTarget = "1.8"
	}
}

dependencies {
	implementation(project(":common:util"))
	implementation(project(":common:provider-contract"))
	implementation(project(":common:koin"))

	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.8.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
	implementation ("androidx.activity:activity-ktx:1.8.2")
	implementation ("androidx.fragment:fragment-ktx:1.6.2")

	implementation("com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.9")

	val room_version = "2.6.0"
	implementation("androidx.room:room-runtime:$room_version")
	implementation("androidx.room:room-ktx:$room_version")
	ksp("androidx.room:room-compiler:$room_version")
}