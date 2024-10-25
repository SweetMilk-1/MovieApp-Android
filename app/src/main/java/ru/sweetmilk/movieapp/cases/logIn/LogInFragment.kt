package ru.sweetmilk.movieapp.cases.logIn

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.sweetmilk.movieapp.MovieApp
import ru.sweetmilk.movieapp.databinding.FragmentLogInBinding
import javax.inject.Inject

class LogInFragment : Fragment() {
    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    private val viewModel: LogInViewModel by viewModels<LogInViewModel> {
        viewModelProvider
    }

    private var _binding: FragmentLogInBinding? = null
    private val binding: FragmentLogInBinding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MovieApp).appComponent
            .logInComponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}