package ru.kram.boxsand.broadcast

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kram.boxsand.R
import ru.kram.boxsand.databinding.FragmentRandomnameBinding
import ru.kram.broadcast.random_name.GoAsyncContract
import ru.kram.broadcast.random_name.PeekServiceContract
import ru.kram.broadcast.random_name.RandomNameContract
import ru.kram.broadcast.random_name.RandomNameNonManifestContract

class RandomNameFragment : Fragment(R.layout.fragment_randomname) {

	private val binding by viewBinding(FragmentRandomnameBinding::bind)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		binding.generateNameButton.setOnClickListener {
			binding.randomName.text = names.random()
		}

		binding.sendExplicitManifestButton.setOnClickListener {
			val intent = RandomNameContract.getReceiveIntent(RandomNameContract.RandomName(binding.randomName.text.toString()))
			requireContext().sendBroadcast(intent, RandomNameContract.SEND_PERMISSION)
		}

		binding.sendImplicitNonManifestButton.setOnClickListener {
			val intent = RandomNameNonManifestContract.getReceiveIntent(
				RandomNameNonManifestContract.RandomName(binding.randomName.text.toString())
			)
			requireContext().sendBroadcast(intent)
		}

		binding.goAsyncButton.setOnClickListener {
			Log.d(TAG, "sendBroadcast goAsync")
			requireContext().sendBroadcast(GoAsyncContract.getReceiverIntent())
		}

		binding.sendPeekReceiverButton.setOnClickListener {
			requireContext().sendBroadcast(PeekServiceContract.getReceiverIntent())
		}
	}

	companion object {
		private const val TAG = "RandomNameFragment"

		private val names = listOf(
			"Alex",
			"John",
			"Max",
			"Andrew",
			"Peter",
			"Ivan",
			"Vlad",
			"Dmitry",
			"Michael",
			"Nikita",
			"Artem",
			"Egor",
			"Anton",
			"Roman",
			"Denis",
			"Oleg",
			"Boris",
			"Vitaly",
			"Alexander",
			"Victor",
			"Valery",
			"Vladimir",
			"Evgeny",
			"Igor",
			"Konstantin",
			"Yuri",
			"Timur",
			"Ruslan",
			"Pavel",
			"Kirill",
			"Nikolay",
			"Grigory",
			"Mikhail",
			"Semyon",
			"Fedor",
			"Leonid",
			"Andrey",
			"Sergey",
			"Vladislav"
		)
	}
}