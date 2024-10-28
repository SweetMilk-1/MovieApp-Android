package ru.sweetmilk.movieapp.cases.auth

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.sweetmilk.movieapp.MovieApp
import ru.sweetmilk.movieapp.R
import ru.sweetmilk.movieapp.databinding.FragmentAuthBinding
import ru.sweetmilk.movieapp.utils.SnackBarUtil
import ru.sweetmilk.movieapp.validation.ValidationError
import java.nio.file.Watchable
import javax.inject.Inject

class AuthFragment : Fragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AuthViewModel> {
        viewModelProviderFactory
    }

    private var _binding: FragmentAuthBinding? = null
    private val binding: FragmentAuthBinding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MovieApp).appComponent
            .addAuthComponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.loginField.apply {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.login = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

        binding.passwordField.apply {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.password = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

        binding.logInButton.apply {
            setOnClickListener {
                clearErrors()
                if (validateAndShowErrors()) {
                    viewModel.authorize()
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.loginField.isEnabled = !it
            binding.passwordField.isEnabled = !it
            binding.logInButton.isEnabled = !it
            binding.progressBar.isVisible = it
        }

        viewModel.serverMessageEvent.observe(viewLifecycleOwner) {
            SnackBarUtil.showSnackBar(requireView(), it)
        }

        viewModel.isAuthorizedEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_auth_to_user_page)
        }
    }

    private fun clearErrors() {
        binding.loginFieldLayout.error = null
        binding.passwordFieldLayout.error = null
    }

    private fun validateAndShowErrors(): Boolean {
        val errors = viewModel.validate()
        if (errors.isEmpty())
            return true
        for ((key, value) in errors) {
            val msg = getErrorStringMessage(value)
            when (key) {
                LOGIN_FIELD -> {
                    binding.loginFieldLayout.error = msg
                }

                PASSWORD_FIELD -> {
                    binding.passwordFieldLayout.error = msg
                }
            }
        }
        return false
    }

    private fun getErrorStringMessage(validationError: ValidationError): String? {
        return when (validationError) {
            ValidationError.Null ->
                getString(R.string.field_is_required)

            ValidationError.Empty ->
                getString(R.string.field_is_required)

            is ValidationError.LessThenMinLength ->
                getString(R.string.field_must_be_more_then, validationError.minLength)

            else -> null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val LOGIN_FIELD = "LOGIN_FIELD"
        const val PASSWORD_FIELD = "PASSWORD_FIELD"
    }
}