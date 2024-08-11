package ru.kram.sandbox

import android.os.Bundle
import android.os.HandlerThread
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import ru.kram.sandbox.navigation.NavigationFragment
import ru.kram.sandbox.pendingintent.PendingIntentFragment

class MainActivity : FragmentActivity() {

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
		reportFullyDrawn()
		enableEdgeToEdge()

		val numFromPendingIntentFragment = intent?.extras?.getInt(PendingIntentFragment.VALUE_KEY, -1)
		if (numFromPendingIntentFragment != null && numFromPendingIntentFragment != -1) {
			Toast.makeText(this, "Value from PendingIntentFragment: $numFromPendingIntentFragment", Toast.LENGTH_LONG).show()
		}
	}
}
