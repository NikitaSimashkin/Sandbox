pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
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
		google {
			content {
				includeGroupByRegex("com\\.android.*")
				includeGroupByRegex("com\\.google.*")
				includeGroupByRegex("androidx.*")
			}
		}
        mavenCentral()
    }
}

rootProject.name = "Sandbox"

apply(from = "common/settings-common.gradle.kts")

include(":app")
include(":boxsand")
include(":sandlib")
include(":deathstar")
include(":common")
include(":provider")
include(":common:provider-contract")
include(":common:koin")
include(":common:broadcast-random-name")
