package ru.kram.sandbox.head

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope
import ru.kram.sandbox.features.biglist.di.getBigListModule
import ru.kram.sandbox.features.biglist.domain.FragmentOpener
import ru.kram.sandbox.features.pendingintent.ActivityComponentNameProvider
import ru.kram.sandbox.features.pendingintent.PendingIntentFragment
import ru.kram.sandbox.features.pendingintent.di.getPendingIntentModule
import ru.kram.sandbox.features.service.ActivityIntentProvider
import ru.kram.sandbox.features.service.di.getServiceModule
import ru.kram.sandbox.head.navigation.NavigationFragment

class MainActivity : FragmentActivity(), KoinScopeComponent {

	override val scope: Scope = createScope()

	private val bigListModule by lazy { getBigListModule(scope.get<FragmentOpener>()) }
	private val pendingIntentModule by lazy { getPendingIntentModule(scope.get<ActivityComponentNameProvider>()) }
	private val serviceModule by lazy { getServiceModule(scope.get<ActivityIntentProvider>()) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		actionBar?.hide()
		if (savedInstanceState == null) {
			supportFragmentManager.beginTransaction().apply {
				add(R.id.main_container_fragment, NavigationFragment())
				commit()
			}
		}

		getKoin().loadModules(getModules())
		reportFullyDrawn()
		enableEdgeToEdge()

		val numFromPendingIntentFragment = intent?.extras?.getInt(PendingIntentFragment.VALUE_KEY, -1)
		if (numFromPendingIntentFragment != null && numFromPendingIntentFragment != -1) {
			Toast.makeText(this, "Value from PendingIntentFragment: $numFromPendingIntentFragment", Toast.LENGTH_LONG).show()
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		scope.close()
		getKoin().unloadModules(getModules())
	}

	private fun getModules() = listOf(
		bigListModule,
		pendingIntentModule,
		serviceModule,
	)
}
