plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

@Suppress("UnstableApiUsage")
android {

    defaultConfig {
		namespace = "ru.kram.sandbox"
        applicationId = "ru.kram.sandbox"
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

    buildFeatures {
        viewBinding = true
		compose = true
    }

	composeOptions {
		kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
	}

	testOptions {
		unitTests {
			isIncludeAndroidResources = true
		}

	}

}

tasks.withType<Test> {
	useJUnitPlatform()
}

dependencies {
	implementation(project(":common:service-contract"))
	implementation(project(":common:deathstar-contract"))
	implementation(project(":common:provider-contract"))
	implementation(project(":common:broadcast-random-name"))
	implementation(project(":common:util"))

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
	testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
	implementation("io.reactivex.rxjava3:rxjava:3.1.6")

	implementation ("androidx.activity:activity-ktx:1.6.2")
	implementation ("androidx.fragment:fragment-ktx:1.6.2")
	implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

	testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
	testImplementation ("org.robolectric:robolectric:4.11.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.media3:media3-exoplayer:1.0.1")
    implementation("com.yandex.android:mobileads:5.9.0")
    implementation("com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.9")

	implementation("androidx.work:work-runtime-ktx:2.9.0")

	implementation ("com.github.bumptech.glide:glide:4.16.0")

	implementation(libs.activityCompose)
	implementation(libs.composeUi)
	implementation(libs.materialCompose)
	implementation(libs.composeRuntimeLiveData)
	implementation(libs.viewModelCompose)
	implementation(libs.navigationCompose)
	implementation(libs.coil)
	implementation(libs.composeUiTooling)
	implementation(libs.composeUiToolingPreview)
    implementation(libs.composeLifecycle)
}
