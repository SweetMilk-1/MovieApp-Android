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
        (requireActivity().application as MovieApp)
            .appComponent
            .addUserPageComponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = viewModel.getUserId()
        val contentFragment = if (userId == null) {
            AuthChooseFragment.newInstance()
        } else {
            UserDataFragment.newInstance(userId)
        }
        childFragmentManager.commit {
            replace(R.id.user_page_fragment_container, contentFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}