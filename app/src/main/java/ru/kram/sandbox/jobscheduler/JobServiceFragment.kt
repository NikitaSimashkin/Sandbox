package ru.kram.sandbox.jobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.sandbox.R
import ru.kram.sandbox.databinding.FragmentJobServiceBinding

class JobServiceFragment: Fragment(R.layout.fragment_job_service) {

	private val binding by viewBinding(FragmentJobServiceBinding::bind)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		binding.scheduleJob.setOnClickListener {
			val jobInfo = JobInfo.Builder(
				NotificationJobService.JOB_ID,
				ComponentName(requireContext().packageName, NotificationJobService::class.java.name)
			)
				.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
				.setRequiresCharging(false)
				.setPeriodic(15 * 60 * 1000)
				.build()

			val jobScheduler = requireContext().getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
			jobScheduler.schedule(jobInfo)
		}

		binding.cancelJob.setOnClickListener {
			val jobScheduler = requireContext().getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
			jobScheduler.cancel(NotificationJobService.JOB_ID)
		}
	}
}