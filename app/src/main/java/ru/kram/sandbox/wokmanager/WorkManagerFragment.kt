package ru.kram.sandbox.wokmanager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.sandbox.R
import ru.kram.sandbox.databinding.FragmentWorkManagerBinding
import java.util.concurrent.TimeUnit

class WorkManagerFragment : Fragment(R.layout.fragment_work_manager) {

	private val binding by viewBinding(FragmentWorkManagerBinding::bind)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		binding.startWork.setOnClickListener {
			val request = PeriodicWorkRequestBuilder<NotificationWorker>(
				30,
				TimeUnit.MINUTES,
				25,
				TimeUnit.MINUTES
			)
				.addTag(NotificationWorker.TAG)
				.setConstraints(Constraints.Builder().setRequiresCharging(false).build())
				.setInputData(Data.Builder().putString("key", "value").build())
				.build()

			WorkManager.getInstance(requireContext()).enqueue(request)
			// enqueue - параллельное выполнение, beginWith().then() - последовательное выполнение
		}

		binding.cancelWork.setOnClickListener {
			WorkManager.getInstance(requireContext()).cancelAllWorkByTag(NotificationWorker.TAG)
		}

		WorkManager.getInstance(requireContext()).getWorkInfosByTagLiveData(NotificationWorker.TAG)
			.observe(viewLifecycleOwner) {
				binding.state.text = it.joinToString("\n") { workInfo ->
					"${workInfo.id} ${workInfo.state}"
				}
			}
	}
}