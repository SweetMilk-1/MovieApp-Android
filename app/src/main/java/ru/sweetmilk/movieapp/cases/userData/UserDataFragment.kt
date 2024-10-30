package ru.sweetmilk.movieapp.cases.userData

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.sweetmilk.movieapp.MovieApp
import ru.sweetmilk.movieapp.R
import ru.sweetmilk.movieapp.api.models.user.User
import ru.sweetmilk.movieapp.databinding.FragmentUserDataBinding
import java.util.UUID
import javax.inject.Inject

class UserDataFragment : Fragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private val viewModel: UserDataViewModel by viewModels<UserDataViewModel> {
        viewModelProviderFactory
    }

    private var _binding: FragmentUserDataBinding? = null
    private val binding: FragmentUserDataBinding get() = _binding!!

    private lateinit var userId: UUID

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MovieApp)
            .appComponent
            .addUserDataComponent()
            .create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userIdStr = arguments?.getString(USER_ID_ARG)
        userId = UUID.fromString(userIdStr)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.userDataContent.isVisible = !it
            binding.progressBar.isVisible = it
        }

        viewModel.loadUserData(userId).observe(viewLifecycleOwner) {
            if (it == null)
                return@observe
            updateUI(it)
        }

        binding.logOut.setOnClickListener {
            LogOutDialogFragment {
                viewModel.logOut()
                findNavController().navigate(R.id.action_user_data_to_user_page)
            }.show(childFragmentManager, LogOutDialogFragment.TAG)
        }
    }

    private fun updateUI(user: User) {
        binding.userLogin.text = user.login
        binding.userEmail.text = user.email
        binding.userIsAdmin.isChecked = user.isAdmin

        lifecycleScope.launch {
            var userImage = viewModel.loadUserImage(userId)
            if (userImage == null) {
                userImage =
                    ResourcesCompat.getDrawable(resources, R.drawable.no_photo, null)
            }
            binding.userImage.setImageDrawable(userImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val USER_ID_ARG = "USER_ID_ARG"

        fun getArgsBundle(userId: UUID): Bundle {
            return Bundle().apply {
                putString(USER_ID_ARG, userId.toString())
            }
        }
    }
}