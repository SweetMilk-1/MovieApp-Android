package ru.sweetmilk.movieapp.cases.authChoose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.sweetmilk.movieapp.R
import ru.sweetmilk.movieapp.databinding.FragmentAuthChooseBinding


class AuthChooseFragment : Fragment() {
    private var _binding: FragmentAuthChooseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthChooseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logInButton.setOnClickListener {
            findNavController().navigate(R.id.action_auth_choose_to_auth)
        }

        binding.signInButton.setOnClickListener {
            //TODO перекинуть на страницу регистрации
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): AuthChooseFragment {
            val fragment = AuthChooseFragment()
            return fragment
        }
    }
}