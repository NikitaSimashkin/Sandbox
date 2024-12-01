plugins {
	id("ru.kram.sandbox.library")
}

android {
	namespace = "ru.kram.sandbox.common.contract_random_messenger"
}

dependencies {
	implementation(project(":common:utils"))
}