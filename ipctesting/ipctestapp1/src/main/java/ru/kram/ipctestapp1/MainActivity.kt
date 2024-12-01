package ru.kram.ipctestapp1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.kram.ipctestapp1.navigation.NavigationFragment

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		supportActionBar?.hide()
		if (savedInstanceState == null) {
			supportFragmentManager.beginTransaction().apply {
				add(R.id.main_container_fragment, NavigationFragment())
				commit()
			}
		}
	}
}