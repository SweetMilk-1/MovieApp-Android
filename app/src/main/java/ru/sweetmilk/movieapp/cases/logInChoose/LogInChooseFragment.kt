package ru.sweetmilk.movieapp.cases.logInChoose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ru.sweetmilk.movieapp.R
import ru.sweetmilk.movieapp.databinding.FragmentLogInChooseBinding


class LogInChooseFragment : Fragment() {
    private var _binding: FragmentLogInChooseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInChooseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logInButton.setOnClickListener{
            requireView().findNavController()
                .navigate(R.id.action_choose_to_log_in)
        }

        binding.signInButton.setOnClickListener{
            //перекинуть на страницу регистрации
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}