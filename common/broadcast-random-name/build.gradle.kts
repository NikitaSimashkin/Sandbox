import com.android.build.api.dsl.LibraryBuildType
import com.android.build.api.dsl.LibraryExtension

plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
}

android {
	variantFilter {
		if (name == "debug") {
			ignore = true
		}
	}

	namespace = "ru.kram.broadcast.random_name"
	compileSdk = 34

	defaultConfig {
		minSdk = 24

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		debug {
			matchingFallbacks.add("release")
		}
		release {
			isMinifyEnabled = false
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
}