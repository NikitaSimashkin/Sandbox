plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
}

@Suppress("UnstableApiUsage")
android {
	compileSdk = 34

	defaultConfig {
		namespace = "ru.kram.deathstar"
		applicationId = "ru.kram.deathstar"
		minSdk = 24
		targetSdk = 33
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		named("debug") {
			@Suppress("INCUBATING")
			isMinifyEnabled = false
		}
		named("release") {
			isMinifyEnabled = true
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}

	kotlinOptions {
		jvmTarget = "1.8"
	}
}

dependencies {
	implementation(project(":common:util"))
	implementation(project(":common:deathstar-contract"))
}