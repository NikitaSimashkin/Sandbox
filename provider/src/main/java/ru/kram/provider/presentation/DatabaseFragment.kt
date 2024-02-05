package ru.kram.provider.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeActivity
import ru.kram.provider.R
import ru.kram.provider.databinding.FragmentDatabaseBinding
import ru.kram.provider.presentation.navigation.DatabaseRouter

class DatabaseFragment: Fragment(R.layout.fragment_database) {

	private val binding by viewBinding(FragmentDatabaseBinding::bind)
	private val router by lazy { requireActivity().get<DatabaseRouter>() }

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.addUserButton.setOnClickListener {
			router.navigateAddUserFragment()
		}
		binding.userlistButton.setOnClickListener {
			router.navigateUserListFragment()
		}
	}
}