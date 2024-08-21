plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.vk.vkompose") version "0.5.5"
    id("com.github.takahirom.decomposer")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "2.0.10"
    kotlin("kapt")
}

vkompose {
    skippabilityCheck {
        stabilityConfigurationPath = "/path/file.config"
        isEnabled = false
    }

    recompose {
        isHighlighterEnabled = true
        isLoggerEnabled = true
        // or
        logger {
            logModifierChanges = true // true by default since 0.5
            logFunctionChanges = true // true by default since 0.5. log when function arguments (like lambdas or function references) of composable function are changed
        }
    }

    testTag {
        isApplierEnabled = true
        isDrawerEnabled = false
        isCleanerEnabled = false
    }

    sourceInformationClean = true
}

kapt {
    correctErrorTypes = true
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

    lint {
        baseline = file("lint-baseline.xml")
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
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.7")

    val composeBom = platform("androidx.compose:compose-bom:2024.05.00")
    implementation(composeBom)

    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")

    implementation("com.airbnb.android:epoxy:5.1.4")
    kapt("com.airbnb.android:epoxy-processor:5.1.4")

	implementation(libs.activityCompose)
	implementation(libs.composeUi)
	implementation(libs.material3Compose)
    implementation(libs.materialCompose)
	implementation(libs.composeRuntimeLiveData)
	implementation(libs.viewModelCompose)
	implementation(libs.navigationCompose)
	implementation(libs.coil)
	implementation(libs.composeUiTooling)
	implementation(libs.composeUiToolingPreview)
    implementation(libs.composeLifecycle)
    implementation(project(":common:koin"))

    val roomVersion = "2.6.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    implementation("io.ktor:ktor-client-core:2.0.0")
    implementation("io.ktor:ktor-client-cio:2.0.0")
    implementation("io.ktor:ktor-client-content-negotiation:2.0.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.0")
    implementation("io.ktor:ktor-client-logging:2.0.0")

    val pagingVersion = "3.3.2"
    implementation("androidx.paging:paging-runtime:$pagingVersion")
    implementation("androidx.paging:paging-compose:$pagingVersion")
    implementation("androidx.room:room-paging:$roomVersion")

    implementation("com.jakewharton.timber:timber:5.0.1")
}
