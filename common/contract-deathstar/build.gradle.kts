plugins {
	id("ru.kram.sandbox.library")
}

android {
	namespace = "ru.kram.sandbox.common.contract_deathstar"

	buildFeatures {
		aidl = true
	}
}