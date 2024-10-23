package ru.sweetmilk.movieapp.cases.userAuth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.sweetmilk.movieapp.MovieApp
import ru.sweetmilk.movieapp.databinding.FragmentUserAuthBinding
import ru.sweetmilk.movieapp.utils.SnackBarUtil
import javax.inject.Inject

class UserAuthFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: UserAuthFragmentViewModel by viewModels<UserAuthFragmentViewModel> {
        viewModelFactory
    }

    private var _binding: FragmentUserAuthBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MovieApp).appComponent
            .addUserAuthComponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.autharization.setOnClickListener {
            viewModel.authorize()
        }

        binding.refresh.setOnClickListener {
            viewModel.refresh()
        }

        viewModel.snackBarLiveEvent.observe(viewLifecycleOwner) {
            SnackBarUtil.showSnackBar(requireView(), it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}