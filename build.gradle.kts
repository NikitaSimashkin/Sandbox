import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryPlugin

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
	id("com.android.application") version "7.4.2" apply false
	id("com.android.library") version "7.4.2" apply false
	id ("org.jetbrains.kotlin.android") version "1.9.10" apply false
	id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
	id("com.github.takahirom.decomposer") apply false
}

subprojects {
	plugins.matching { it is AppPlugin || it is LibraryPlugin }.whenPluginAdded {
		configure<BaseExtension> {
			setCompileSdkVersion(34)

			defaultConfig {
				minSdk = 24
				targetSdk = 33
			}
		}
	}

	tasks.whenTaskAdded {
		if (project.name == "boxsand" && this.name == "assembleRelease") {
			enabled = false
		}
	}

	configurations.all {
		resolutionStrategy {
			force("androidx.lifecycle:lifecycle-livedata-core:2.7.0")
		}
	}
}

subprojects {
	tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).configureEach {
		kotlinOptions {
			if (project.findProperty("myapp.enableComposeCompilerReports") == "true") {
				freeCompilerArgs = freeCompilerArgs + listOf(
					"-P",
					"plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
							project.buildDir.absolutePath + "/compose_metrics"
				) + listOf(
					"-P",
					"plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
							project.buildDir.absolutePath + "/compose_metrics"
				)
			}
		}
	}
}