import ru.kram.sandbox.build.utils.setupCompose

plugins {
    id("ru.kram.sandbox.application")
    kotlin("plugin.serialization")
}

android {
    defaultConfig {
		namespace = "ru.kram.sandbox.head"
        applicationId = "ru.kram.sandbox.head"
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }

    packaging {
        resources.excludes.add("META-INF/*")
    }
}

setupCompose()

tasks.withType<Test> {
	useJUnitPlatform()
}

dependencies {
    implementation(project(":common:koin"))
    implementation(project(":common:core"))
    implementation(project(":common:utils"))
    implementation(project(":common:test"))

	implementation(project(":common:contract-random-messenger"))
	implementation(project(":common:contract-deathstar"))
	implementation(project(":common:contract-user-provider"))
	implementation(project(":common:contract-broadcast-random-name"))

    implementation(project(":features:ad"))
    implementation(project(":features:biglist"))
    implementation(project(":features:broadcast-random-number"))
    implementation(project(":features:carousel"))
    implementation(project(":features:collect"))
    implementation(project(":features:compose"))
    implementation(project(":features:contentprovider"))
    implementation(project(":features:drawservice"))
    implementation(project(":features:edittext"))
    implementation(project(":features:experiments"))
    implementation(project(":features:jobscheduler"))
    implementation(project(":features:language"))
    implementation(project(":features:pendingintent"))
    implementation(project(":features:player"))
    implementation(project(":features:recyclerfocus"))
    implementation(project(":features:rx"))
    implementation(project(":features:service"))
    implementation(project(":features:sparsearray"))
    implementation(project(":features:textviewtest"))
    implementation(project(":features:workmanager"))


    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.google.material)

    implementation(libs.rxjava3)

    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)

    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel)

    implementation(libs.mobileads)
    implementation(libs.viewbinding.delegate)
    implementation(libs.worknamager)
    implementation(libs.glide)
    implementation(libs.immutable.collections)
    implementation(libs.koin.compose)
}
