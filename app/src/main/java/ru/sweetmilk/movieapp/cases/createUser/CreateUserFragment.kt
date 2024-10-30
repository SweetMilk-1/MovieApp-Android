package ru.sweetmilk.movieapp.cases.createUser

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.sweetmilk.movieapp.MovieApp
import ru.sweetmilk.movieapp.R
import ru.sweetmilk.movieapp.databinding.FragmentCreateUserBinding
import ru.sweetmilk.movieapp.validation.ValidationError
import javax.inject.Inject

class CreateUserFragment : Fragment() {
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private val viewModel: CreateUserViewModel by viewModels {
        viewModelProviderFactory
    }

    private var _binding: FragmentCreateUserBinding? = null
    val binding: FragmentCreateUserBinding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MovieApp).appComponent
            .addCreateUserComponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

        binding.emailField.apply {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.email = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

        binding.repeatPasswordField.apply {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.repeatPassword = s.toString()
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

        binding.createUserButton.apply {
            setOnClickListener {
                clearErrors()
                if (!validateAndShowErrors() || !viewModel.isPasswordsSame(getString(R.string.passwors_not_same)))
                    return@setOnClickListener
                viewModel.createUser()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.loginField.isEnabled = !it
            binding.emailField.isEnabled = !it
            binding.passwordField.isEnabled = !it
            binding.repeatPasswordField.isEnabled = !it
            binding.createUserButton.isEnabled = !it
            binding.errorMessageView.isVisible = !it

            binding.progressBar.isVisible = it
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.errorMessageView.text = it
        }

        viewModel.userCreatedEvent.observe(viewLifecycleOwner) {
            if (it) {
                UserCreatedDialog {
                    findNavController().navigate(R.id.action_create_user_to_auth)
                }.show(childFragmentManager, UserCreatedDialog.TAG)
            }
        }
    }


    private fun clearErrors() {
        binding.loginFieldLayout.error = null
        binding.emailFieldLayout.error = null
        binding.passwordFieldLayout.error = null
        binding.repeatPasswordFieldLayout.error = null
        binding.errorMessageView.text = null
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

                EMAIL_FIELD -> {
                    binding.emailFieldLayout.error = msg
                }

                PASSWORD_FIELD -> {
                    binding.passwordFieldLayout.error = msg
                }

                REPEAT_PASSWORD_FIELD -> {
                    binding.repeatPasswordFieldLayout.error = msg
                }
            }
        }
        return false
    }

    private fun getErrorStringMessage(validationError: ValidationError): CharSequence? {
        return when (validationError) {
            ValidationError.Null ->
                getString(R.string.field_is_required)

            ValidationError.Empty ->
                getString(R.string.field_is_required)

            is ValidationError.LessThenMinLength ->
                getString(R.string.field_must_be_more_then, validationError.minLength)

            ValidationError.NotEmail ->
                getString(R.string.invalid_email)

            else -> null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val LOGIN_FIELD = "LOGIN_FIELD"
        const val EMAIL_FIELD = "EMAIL_FIELD"
        const val PASSWORD_FIELD = "PASSWORD_FIELD"
        const val REPEAT_PASSWORD_FIELD = "REPEAT_PASSWORD_FIELD"
    }
}