package ru.kram.sandbox.features.contentprovider

import android.Manifest
import android.annotation.SuppressLint
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import ru.kram.sandbox.common.utils.KResult
import ru.kram.sandbox.common.contract_user_provider.UserProviderApi
import ru.kram.sandbox.features.contentprovider.databinding.FragmentProviderBinding
import timber.log.Timber
import kotlin.random.Random

class ProviderFragment : Fragment(R.layout.fragment_provider) {

	private val binding by viewBinding(FragmentProviderBinding::bind)
	private val requestContactPermission =
		registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		requestContactPermission.launch(Manifest.permission.READ_CONTACTS)

		binding.requstContact.setOnClickListener(::requestContacts)
		binding.requestUsers.setOnClickListener(::requestUsers)

		requireContext().contentResolver.registerContentObserver(
			UserProviderApi.BASE_URI,
			true,
			object : ContentObserver(Handler(Looper.getMainLooper())) {
				override fun onChange(selfChange: Boolean) {
					Timber.d("onChange")
					super.onChange(selfChange)
					requestUsers(view)
					Toast.makeText(requireContext().applicationContext, "Hello from SandBoxApp!", Toast.LENGTH_SHORT)
						.show()
				}
			})
	}

	@SuppressLint("Range")
	private fun requestContacts(view: View) {
		val limit = Random(System.currentTimeMillis()).nextInt(50)
		val contentResolver = requireContext().contentResolver
		val projection =
			arrayOf(ContactsContract.Data.CONTACT_ID, ContactsContract.Contacts.HAS_PHONE_NUMBER)
		val idsCursor = contentResolver.query(
			ContactsContract.Data.CONTENT_URI,
			projection,
			ContactsContract.Contacts.HAS_PHONE_NUMBER + " > 0",
			null,
			projection[0] + " LIMIT $limit"
		)

		val ids = IntArray(limit)
		idsCursor?.let {
			var i = 0
			while (it.moveToNext()) {
				ids[i] = it.getInt(it.getColumnIndex(projection[0]))
				i++
			}
		}

		idsCursor?.close()

		val phoneProjection = arrayOf(
			ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
			ContactsContract.CommonDataKinds.Phone.NUMBER,
			ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
		)
		val phoneCursor = contentResolver.query(
			ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
			phoneProjection,
			"${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} in (${ids.joinToString(",")})",
			null,
			"LIMIT $limit"
		)

		val result = StringBuilder()
		val added = mutableSetOf<String>()
		phoneCursor?.let {
			while (it.moveToNext()) {
				val number = it.getString(it.getColumnIndex(phoneProjection[1]))
				val name = it.getString(it.getColumnIndex(phoneProjection[2]))
				if (added.contains(name)) continue

				added.add(name)
				result.append("$name: $number\n")
			}
		}

		binding.textForSomething.text = result.toString()
	}

	private fun requestUsers(view: View) {
		val userProviderApi: UserProviderApi =
			UserProviderApi.create(requireContext().contentResolver)
		lifecycleScope.launch {
			val users = when (val result = userProviderApi.getUsers()) {
				is KResult.Success -> result.data
				is KResult.Error -> emptyList()
			}
			binding.textForSomething.text = users.joinToString("\n\n\n")
		}
	}

	companion object {
		private const val TAG = "ProviderFragment"
	}
}