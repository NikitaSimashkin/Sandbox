plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

dependencies {
    implementation(libs.plugin.android)
    implementation(libs.plugin.kotlin)
    implementation(libs.plugin.cache.fix)
    implementation(libs.plugin.compose)
    implementation(libs.plugin.vkompose)
    implementation(gradleApi())
    implementation(localGroovy())
}


gradlePlugin {
    plugins {
        create("library") {
            id = "ru.kram.sandbox.library"
            implementationClass = "ru.kram.sandbox.build.plugins.BaseLibraryPlugin"
        }
    }
    plugins {
        create("application") {
            id = "ru.kram.sandbox.application"
            implementationClass = "ru.kram.sandbox.build.plugins.BaseAppPlugin"
        }
    }
    plugins {
        create("compose") {
            id = "ru.kram.sandbox.compose"
            implementationClass = "ru.kram.sandbox.build.plugins.ComposePlugin"
        }
    }
    plugins {
        create("vkompose") {
            id = "ru.kram.sandbox.vkompose"
            implementationClass = "ru.kram.sandbox.build.plugins.VkomposePlugin"
        }
    }
}