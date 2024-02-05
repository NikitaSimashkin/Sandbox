package ru.kram.provider.presentation

import android.os.Bundle
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeActivity
import ru.kram.provider.R
import ru.kram.provider.presentation.navigation.DatabaseRouter

class ProviderActivity : ScopeActivity() {

	private val router: DatabaseRouter by inject()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_provider)
		supportActionBar?.hide()
		if (savedInstanceState == null) {
			router.navigateDatabaseFragment()
		}
	}

	companion object {
		private const val TAG = "ProviderActivity"
	}
}