plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
}

@Suppress("UnstableApiUsage")
android {
	namespace = "ru.kram.boxsand"
	compileSdk = 33

	defaultConfig {
		applicationId = "ru.kram.boxsand"
		minSdk = 24
		targetSdk = 33
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}

	lint {
		checkReleaseBuilds = false
	}

	buildFeatures {
		viewBinding = true
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
	implementation(project(":common:service-contract"))
	implementation(project(":common:util"))
	implementation(project(":common:broadcast-random-name"))

	implementation("androidx.core:core-ktx:1.7.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.8.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
	testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
	implementation("io.reactivex.rxjava3:rxjava:3.1.6")

	implementation ("androidx.activity:activity-ktx:1.6.2")
	implementation ("androidx.fragment:fragment-ktx:1.6.2")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

	implementation("androidx.media3:media3-exoplayer:1.0.1")
	//noinspection MobileAdsSdkOutdatedVersion
	implementation("com.yandex.android:mobileads:5.9.0")
	implementation("com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.9")
}