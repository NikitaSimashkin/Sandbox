package ru.kram.provider.presentation.navigation

import androidx.fragment.app.Fragment
import ru.kram.provider.R
import ru.kram.provider.presentation.DatabaseFragment
import ru.kram.provider.presentation.ProviderActivity
import ru.kram.provider.presentation.adduser.AddUserFragment
import ru.kram.provider.presentation.updateuser.UpdateUserFragment
import ru.kram.provider.presentation.userlist.UserListFragment

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