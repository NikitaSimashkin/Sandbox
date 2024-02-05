pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
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

include(":app")
include(":boxsand")
include(":sandlib")
include(":deathstar")
include(":common")
include(":provider")
include(":common:provider-contract")
include(":common:koin")
