package ru.kram.userprovider.presentation.userlist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.scope.ScopeFragment
import org.koin.core.context.GlobalContext
import ru.kram.userprovider.R
import ru.kram.userprovider.databinding.FragmentUserlistBinding
import ru.kram.userprovider.presentation.model.UserUi
import ru.kram.userprovider.presentation.navigation.DatabaseRouter
import ru.kram.userprovider.presentation.userlist.recycler.UserAdapter
import timber.log.Timber

class UserListFragment: ScopeFragment(R.layout.fragment_userlist) {

	private val binding: FragmentUserlistBinding by viewBinding(FragmentUserlistBinding::bind)
	private val viewModel: UserlistViewModel by viewModels{ GlobalContext.get().get<UserlistViewModelFactory>() }

	private val router by lazy { requireActivity().get<DatabaseRouter>() }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel.collectUsers()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		setUpRecyclerView()
		lifecycleScope.launch {
			viewModel.users.collect {
				Timber.d("users: $it")
				updateUsers(it)
			}
		}
	}

	private fun setUpRecyclerView() {
		val onUserClickListener = { user: UserUi ->
			Timber.d("on click user: $user")
			router.navigateUpdateUserFragment(user.id)
		}
		val onRemoveButtonClickListener = { user: UserUi ->
			Timber.d("on remove user: $user")
			viewModel.removeUser(user.id)
		}
		with(binding.userList) {
			adapter = UserAdapter(onUserClickListener, onRemoveButtonClickListener)
			layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
			itemAnimator = null
		}
	}

	private fun updateUsers(users: List<UserUi>) {
		(binding.userList.adapter as UserAdapter).setUsers(users)
	}
}
