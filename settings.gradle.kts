pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
		maven("https://jitpack.io")
    }

	resolutionStrategy {
		eachPlugin {
			if (requested.id.toString() == "com.github.takahirom.decomposer") {
				useModule("com.github.takahirom:decomposer:main-SNAPSHOT")
			}
		}
	}
}

plugins {
	id("com.gradle.enterprise") version ("3.16.2")
}

gradleEnterprise {
	buildScan {
		termsOfServiceUrl = "https://gradle.com/terms-of-service"
		termsOfServiceAgree = "yes"
	}
}

buildCache {
	local {
		directory = file(".gradle/build-cache")
		isEnabled = true
		isPush = true
	}
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
		google()
        mavenCentral()
    }
}

rootProject.name = "Sandbox"

apply(from = "common/settings-common.gradle.kts")
apply(from = "features/settings-features.gradle.kts")
apply(from = "ipctesting/settings-ipctesting.gradle.kts")

includeBuild("gradle-plugins")

include(":head")
include(":common")
include(":features")
include(":pokemonpaging")
