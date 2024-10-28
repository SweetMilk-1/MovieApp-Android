package ru.sweetmilk.movieapp.cases.userPage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ru.sweetmilk.movieapp.MovieApp
import ru.sweetmilk.movieapp.R
import ru.sweetmilk.movieapp.cases.authChoose.AuthChooseFragment
import ru.sweetmilk.movieapp.cases.userData.UserDataFragment
import ru.sweetmilk.movieapp.databinding.FragmentUserPageBinding
import javax.inject.Inject

class UserPageFragment : Fragment() {
    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    private val viewModel by viewModels<UserPageViewModel> {
        viewModelProvider
    }

    private var _binding: FragmentUserPageBinding? = null
    private val binding: FragmentUserPageBinding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MovieApp).appComponent
            .addUserPageComponent()
            .create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = viewModel.getUserId()
        val navController = findNavController()
        if (userId == null) {
            navController.navigate(R.id.action_user_page_to_auth_choose)
        } else {
            val args = UserDataFragment.getArgsBundle(userId)
            navController
                .navigate(R.id.action_user_page_to_user_data, args)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}