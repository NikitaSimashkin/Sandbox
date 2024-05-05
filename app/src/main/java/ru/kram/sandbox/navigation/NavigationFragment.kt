package ru.kram.sandbox.navigation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.sandbox.R
import ru.kram.sandbox.add.AddFragment
import ru.kram.sandbox.biglist.presentation.BigListFragment
import ru.kram.sandbox.broadcast.ReceiverFragment
import ru.kram.sandbox.carousel.CarouselFragment
import ru.kram.sandbox.carousel2.CarouselFragment2
import ru.kram.sandbox.compose.ComposeNavigationFragment
import ru.kram.sandbox.compose.edgetoedge.EdgeToEdgeActivity
import ru.kram.sandbox.contentprovider.ProviderFragment
import ru.kram.sandbox.databinding.FragmentNavigationBinding
import ru.kram.sandbox.drawservice.DrawServiceFragment
import ru.kram.sandbox.edittext.EditTextFragment
import ru.kram.sandbox.jobscheduler.JobServiceFragment
import ru.kram.sandbox.pendingintent.PendingIntentFragment
import ru.kram.sandbox.player.PlayerFragment
import ru.kram.sandbox.recyclerfocus.RecyclerFragment
import ru.kram.sandbox.rx.RxFragment
import ru.kram.sandbox.service.ServiceFragment
import ru.kram.sandbox.textviewtest.TextViewTestFragment
import ru.kram.sandbox.wokmanager.WorkManagerFragment
import java.util.PriorityQueue
import java.util.Queue

class NavigationFragment: Fragment(R.layout.fragment_navigation) {

	private val binding by viewBinding(FragmentNavigationBinding::bind)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
		exoPlayerButton.setOnClickListener {
			openFragment(PlayerFragment())
		}
		addButton.setOnClickListener {
			openFragment(AddFragment())
		}
		recyclerFocusButton.setOnClickListener {
			openFragment(RecyclerFragment())
		}
		testTextviewButton.setOnClickListener {
			openFragment(TextViewTestFragment())
		}
		carouselButton.setOnClickListener {
			openFragment(CarouselFragment())
		}
		carousel2Button.setOnClickListener {
			openFragment(CarouselFragment2())
		}
		rxButton.setOnClickListener {
			openFragment(RxFragment())
		}
		serviceButton.setOnClickListener {
			openFragment(ServiceFragment())
		}
		providerButton.setOnClickListener {
			openFragment(ProviderFragment())
		}
		receiverButton.setOnClickListener {
			openFragment(ReceiverFragment())
		}
		drawService.setOnClickListener {
			openFragment(DrawServiceFragment())
		}
		pendingIntent.setOnClickListener {
			openFragment(PendingIntentFragment())
		}
		jobServiceButton.setOnClickListener {
			openFragment(JobServiceFragment())
		}
		workManagerButton.setOnClickListener {
			openFragment(WorkManagerFragment())
		}
		bigListButton.setOnClickListener {
			openFragment(BigListFragment())
		}
		editTextView.setOnClickListener {
			openFragment(EditTextFragment())
		}
 		compose.setOnClickListener {
			openFragment(ComposeNavigationFragment())
		}
		edgeToEdge.setOnClickListener {
			startActivity(Intent(requireContext(), EdgeToEdgeActivity::class.java))
		}
	}

	private fun openFragment(fragment: Fragment) {
		parentFragmentManager.beginTransaction().apply {
			addToBackStack(null)
			replace(R.id.main_container_fragment, fragment)
			commit()
		}
	}
}