package ru.sweetmilk.movieapp.cases.userPage

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.sweetmilk.movieapp.MovieApp
import ru.sweetmilk.movieapp.R
import ru.sweetmilk.movieapp.cases.userData.UserDataFragment
import javax.inject.Inject

class UserPageFragment : Fragment() {
    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    private val viewModel by viewModels<UserPageViewModel> {
        viewModelProvider
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MovieApp).appComponent
            .addUserPageComponent()
            .create()
            .inject(this)
    }

    override fun onResume() {
        super.onResume()
        val navController = findNavController()
        val userId = viewModel.getUserId()
        if (userId == null) {
            navController.navigate(R.id.action_user_page_to_auth_choose)
        } else {
            val args = UserDataFragment.getArgsBundle(userId)
            navController
                .navigate(R.id.action_user_page_to_user_data, args)
        }
    }
}