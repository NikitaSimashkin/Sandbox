package ru.kram.sandbox

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.kram.sandbox.carousel.CarouselFragment
import ru.kram.sandbox.navigation.NavigationFragment
import ru.kram.sandbox.recyclerfocus.RecyclerFragment

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		if (savedInstanceState == null) {
			supportFragmentManager.beginTransaction().apply {
				add(R.id.main_container_fragment, NavigationFragment())
				commit()
			}
		}
	}

	override fun onStart() {
		super.onStart()
		supportActionBar?.hide()
	}
}
