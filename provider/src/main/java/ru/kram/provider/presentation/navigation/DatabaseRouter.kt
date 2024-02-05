package ru.kram.provider.presentation.navigation

interface DatabaseRouter {
	fun navigateDatabaseFragment()
	fun navigateAddUserFragment()
	fun navigateUpdateUserFragment(userId: Long)
	fun navigateUserListFragment()
	fun navigateBack()
}