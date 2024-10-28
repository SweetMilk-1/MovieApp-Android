package ru.sweetmilk.movieapp.cases.userData

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.sweetmilk.movieapp.R
import java.util.UUID

class UserDataFragment : Fragment() {
    private val viewModel: UserDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_user_data, container, false)
    }
    companion object {
        private const val USER_ID_ARG = "USER_ID_ARG"

        fun newInstance(userId: UUID): UserDataFragment{
            val args = Bundle().apply {
                putString(USER_ID_ARG, userId.toString())
            }

            val fragment = UserDataFragment()
            fragment.arguments = args
            return fragment
        }
    }
}