package ru.sweetmilk.movieapp.cases.movieList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
import ru.sweetmilk.movieapp.view.pagingBarView.PagingBarView
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

        (requireActivity().application as MovieApp).appComponent
            .addMovieListComponent()
            .create()
            .inject(this)
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

        binding.searchBar.setOnSearchListener {
            hideKeyboard()
            binding.pagingBar.clear()
            viewModel.apply {
                setCurrentPage(1)
                setSearchText(it)
                loadMovieList()
            }
        }

        binding.pagingBar.onPageChangeListener = object : PagingBarView.OnChangePageListener {
            override fun onChangePage(page: Int) {
                viewModel.apply {
                    setCurrentPage(page)
                    loadMovieList()
                }
            }
        }

        viewModel.movieListFragmentState.observe(viewLifecycleOwner) { state ->
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
            adapter.submitList(response?.items)
            progressBar.isVisible = false
            errorView.isVisible = false
            movieList.isVisible = true
            pagingBar.setPagingParameters(
                response?.page ?: 0,
                response?.pageCount ?: 0
            )
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

    private fun hideKeyboard() {
        val view = requireActivity().findViewById<View>(android.R.id.content)
        if (view != null) {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}