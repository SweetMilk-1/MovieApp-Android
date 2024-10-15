package ru.sweetmilk.movieapp.cases.movieList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import ru.sweetmilk.movieapp.MovieApp
import ru.sweetmilk.movieapp.api.models.ErrorResponse
import ru.sweetmilk.movieapp.api.models.MovieListItem
import ru.sweetmilk.movieapp.api.models.PagedResponse
import ru.sweetmilk.movieapp.databinding.FragmentMovieListBinding
import javax.inject.Inject


class MovieListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MovieListFragmentViewModel by viewModels<MovieListFragmentViewModel> {
        viewModelFactory
    }

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MovieListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as MovieApp).appComponent.addMovieListComponent()
            .create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        adapter = MovieListAdapter(
            requireContext(),
            lifecycleScope,
            viewModel::loadMovieImage
        )
        binding.movieList.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            loadMovieList()
            movieListFragmentState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    MovieListFragmentState.Idle -> {
                        handleIdleState()
                    }

                    MovieListFragmentState.Loading -> {
                        handleLoadingState()
                    }

                    is MovieListFragmentState.Success -> {
                        handleSuccessState(state.data)
                    }

                    is MovieListFragmentState.Failed -> {
                        handleFailedState(state.errorCode, state.errorData)
                    }

                    is MovieListFragmentState.Error -> {
                        handleErrorState(state.e)
                    }
                }
            }
        }
    }

    private fun handleErrorState(throwable: Throwable) {
        binding.apply {
            movieList.isVisible = true
            progressBar.isVisible = false
            errorView.isVisible = true
            errorView.text = throwable.message
        }
    }

    private fun handleFailedState(statusCode: Int, errorData: ErrorResponse) {
        binding.apply {
            movieList.isVisible = true
            progressBar.isVisible = false
            errorView.isVisible = true
            errorView.text = "$statusCode: ${errorData.message}"
        }
    }

    private fun handleSuccessState(response: PagedResponse<MovieListItem>?) {
        binding.apply {
            movieList.isVisible = true
            progressBar.isVisible = false
            errorView.isVisible = false
            adapter.submitList(response?.items)
        }
    }

    private fun handleLoadingState() {
        binding.apply {
            movieList.isVisible = false
            progressBar.isVisible = true
            errorView.isVisible = false
        }
    }

    private fun handleIdleState() {
        binding.apply {
            movieList.isVisible = false
            progressBar.isVisible = false
            errorView.isVisible = false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}