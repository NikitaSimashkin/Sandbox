package ru.kram.sandbox.head.navigation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.sandbox.features.ad.AdFragment
import ru.kram.sandbox.features.biglist.presentation.BigListEpoxyFragment
import ru.kram.sandbox.features.biglist.presentation.BigListFragment
import ru.kram.sandbox.features.broadcast_random_number.ReceiverFragment
import ru.kram.sandbox.head.R
import ru.kram.sandbox.features.carousel.carousel1.CarouselFragment
import ru.kram.sandbox.features.carousel.carousel2.CarouselFragment2
import ru.kram.sandbox.features.compose.ComposeNavigationFragment
import ru.kram.sandbox.features.compose.edgetoedge.EdgeToEdgeActivity
import ru.kram.sandbox.features.compose.optimization.OptimizeNavigationFragment
import ru.kram.sandbox.features.contentprovider.ProviderFragment
import ru.kram.sandbox.features.drawservice.DrawServiceFragment
import ru.kram.sandbox.head.databinding.FragmentNavigationBinding
import ru.kram.sandbox.features.edittext.EditTextFragment
import ru.kram.sandbox.features.jobscheduler.JobServiceFragment
import ru.kram.sandbox.features.textviewtest.TextViewTestFragment
import ru.kram.sandbox.features.workmanager.WorkManagerFragment

class NavigationFragment: Fragment(R.layout.fragment_navigation) {

	private val binding by viewBinding(FragmentNavigationBinding::bind)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
		exoPlayerButton.setOnClickListener {
			openFragment(ru.kram.sandbox.features.player.PlayerFragment())
		}
		adButton.setOnClickListener {
			openFragment(AdFragment())
		}
		recyclerFocusButton.setOnClickListener {
			openFragment(ru.kram.sandbox.features.recyclerfocus.RecyclerFragment())
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
			openFragment(ru.kram.sandbox.features.rx.RxFragment())
		}
		serviceButton.setOnClickListener {
			openFragment(ru.kram.sandbox.features.service.ServiceFragment())
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
			openFragment(ru.kram.sandbox.features.pendingintent.PendingIntentFragment())
		}
		jobServiceButton.setOnClickListener {
			openFragment(JobServiceFragment())
		}
		workManagerButton.setOnClickListener {
			openFragment(ru.kram.sandbox.features.workmanager.WorkManagerFragment())
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
		bigListEpoxy.setOnClickListener {
			openFragment(BigListEpoxyFragment())
		}
		optimizeCompose.setOnClickListener {
			openFragment(OptimizeNavigationFragment())
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