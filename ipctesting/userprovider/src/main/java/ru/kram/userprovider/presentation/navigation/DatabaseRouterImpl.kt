package ru.kram.userprovider.presentation.navigation

import androidx.fragment.app.Fragment
import ru.kram.userprovider.R
import ru.kram.userprovider.presentation.DatabaseFragment
import ru.kram.userprovider.presentation.ProviderActivity
import ru.kram.userprovider.presentation.adduser.AddUserFragment
import ru.kram.userprovider.presentation.updateuser.UpdateUserFragment
import ru.kram.userprovider.presentation.userlist.UserListFragment

class DatabaseRouterImpl(
	private val providerActivity: ProviderActivity
): DatabaseRouter {

	override fun navigateDatabaseFragment() {
		openFragment(DatabaseFragment())
	}

	override fun navigateAddUserFragment() {
		openFragment(AddUserFragment())
	}

	override fun navigateUserListFragment() {
		openFragment(UserListFragment())
	}

	override fun navigateUpdateUserFragment(userId: Long) {
		openFragment(UpdateUserFragment.create(userId))
	}

	override fun navigateBack() {
		providerActivity.supportFragmentManager.popBackStack()
	}

	private fun openFragment(fragment: Fragment) {
		providerActivity.supportFragmentManager.beginTransaction().apply {
			if (providerActivity.supportFragmentManager.fragments.isNotEmpty()) {
				addToBackStack(null)
			}
			replace(R.id.provider_activity_fragment_container, fragment)
			commit()
		}
	}
}