plugins {
	id("ru.kram.sandbox.library")
}

android {
	namespace = "ru.kram.sandbox.common.contract_user_provider"
}

dependencies {
	implementation(project(":common:utils"))
}