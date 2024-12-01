plugins {
	alias(libs.plugins.android.app) apply false
	alias(libs.plugins.android.library) apply false
	alias(libs.plugins.kotlin.android) apply false
	alias(libs.plugins.compose.compiler) apply false
	alias(libs.plugins.ksp) apply false
	alias(libs.plugins.decomposer) apply false
	alias(libs.plugins.kotlin.serialization) apply false
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