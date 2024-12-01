plugins {
	id("ru.kram.sandbox.application")
}

android {
	namespace = "ru.kram.deathstar"
}

dependencies {
	implementation(project(":common:utils"))
	implementation(project(":common:contract-deathstar"))
}